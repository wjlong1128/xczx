package com.wjl.xczx.content.course.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjl.xczx.common.consts.XczxConstant;
import com.wjl.xczx.common.exception.Assert;
import com.wjl.xczx.common.result.Result;
import com.wjl.xczx.common.result.status.StateEnum;
import com.wjl.xczx.common.utils.StringUtil;
import com.wjl.xczx.content.course.exception.CourseException;
import com.wjl.xczx.content.course.exception.state.CourseStateEnum;
import com.wjl.xczx.content.course.mapper.CoursePublishMapper;
import com.wjl.xczx.content.course.model.entity.CourseBase;
import com.wjl.xczx.content.course.model.entity.CourseMarket;
import com.wjl.xczx.content.course.model.entity.CoursePublish;
import com.wjl.xczx.content.course.model.entity.CoursePublishPre;
import com.wjl.xczx.content.course.model.vo.CourseInfoVO;
import com.wjl.xczx.content.course.model.vo.CoursePreviewVO;
import com.wjl.xczx.content.course.model.vo.TeacherVO;
import com.wjl.xczx.content.course.model.vo.TeachplanVO;
import com.wjl.xczx.content.course.service.*;
import com.wjl.xczx.feign.client.MediaServiceClient;
import com.wjl.xczx.feign.config.MultipartSupportConfig;
import com.wjl.xczx.messagesdk.model.po.MqMessage;
import com.wjl.xczx.messagesdk.service.MqMessageService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/18
 * @description
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CoursePublishServiceImpl extends ServiceImpl<CoursePublishMapper, CoursePublish> implements CoursePublishService {

    private final CourseBaseService baseService;
    private final TeachplanService teachplanService;
    private final CourseTeacherService teacherService;
    private final CourseMarketService marketService;
    private final CoursePublishPreService preService;

    private final MediaServiceClient mediaServiceClient;
    @Autowired
    private MqMessageService mqMessageService;

    @Override
    public CoursePreviewVO coursePreview(Long courseId) {
        // 基本信息
        CourseInfoVO courseInfoVO = baseService.getCourseById(courseId);
        // 章节信息
        Result<List<TeachplanVO>> treeNodes = teachplanService.treeNodes(courseId);
        // 师资信息
        List<TeacherVO> teacherVOS = teacherService.courseTeacher(courseId);
        CoursePreviewVO previewVO = new CoursePreviewVO();
        previewVO.setCourseBase(courseInfoVO);
        previewVO.setTeachplans(treeNodes.getData());
        previewVO.setTeachers(teacherVOS);

        return previewVO;
    }

    @Transactional
    @Override
    public void commitAudit(Long companyId, Long courseId) {
        Assert.notNull(companyId, () -> new CourseException(StateEnum.NOT_AUTHENTICATION));
        CourseInfoVO course = baseService.getCourseById(companyId, courseId).getData();
        Assert.notNull(course, () -> new CourseException(CourseStateEnum.QUERY_ERROR));
        Assert.isTrue(course.getCompanyId().equals(companyId), () -> new CourseException(CourseStateEnum.UNAUTHORIZED_ERROR));
        if (!course.getCompanyId().equals(companyId)) {
            throw new CourseException(CourseStateEnum.UNAUTHORIZED_ERROR);
        }

        // 已提交不允许提交 检查状态
        // checkCourse(courseId, course);
        if (XczxConstant.AuditStatus.COMMIT.getCode().equals(course.getAuditStatus())) {
            throw new CourseException(CourseStateEnum.REPEAT_COMMIT_ERROR);
        }
        if (StringUtil.isEmpty(course.getPic())) {
            throw new CourseException(CourseStateEnum.PIC_NOT_NULL);
        }

        List<TeachplanVO> list = teachplanService.treeNodes(courseId).getData();
        if (CollectionUtils.isEmpty(list)) {
            throw new CourseException(CourseStateEnum.COURSE_TEAC_NOT_NULL);
        }
        // 师资...营销...

        // 查询课程信息，计划信息，营销信息，师资信息插入到预发布表
        CoursePublishPre publishPre = new CoursePublishPre();
        // 基本 营销
        BeanUtils.copyProperties(course, publishPre);
        CourseMarket market = marketService.getById(courseId);
        String marketJson = JSON.toJSONString(market);
        publishPre.setMarket(marketJson);
        publishPre.setCompanyId(companyId);
        String teachJson = JSON.toJSONString(list);
        publishPre.setTeachplan(teachJson);
        String commitCode = XczxConstant.AuditStatus.COMMIT.getCode();
        List<TeacherVO> teacherVOS = teacherService.courseTeacher(courseId);
        String teacherJson = JSON.toJSONString(teacherVOS);
        publishPre.setTeachers(teacherJson);
        publishPre.setStName(course.getStName());
        publishPre.setMtName(course.getMtName());
        publishPre.setStatus(commitCode);

        preService.saveOrUpdate(publishPre);
        // 更新课程基本信息表状态
        CourseBase base = baseService.getById(courseId);
        base.setAuditStatus(commitCode);
        baseService.updateById(base);
    }

    @SentinelResource(value= "public_course")
    @Transactional
    @Override
    public void publish(Long companyId, Long courseId) {
        CoursePublishPre publishPre = preService.getById(courseId);
        Assert.notNull(publishPre, () -> new CourseException(CourseStateEnum.COURSE_NOT_AUDIT));
        if (!publishPre.getStatus().equals(XczxConstant.AuditStatus.OK.getCode())) {
            throw new CourseException(CourseStateEnum.UNDER_REVIEW);
        }

        CoursePublish publish = new CoursePublish();
        BeanUtils.copyProperties(publishPre, publish);
        saveOrUpdate(publish);
        // 向消息表写入数据
        MqMessage message = mqMessageService.addMessage("course_publish", courseId.toString(), null, null);
        Assert.notNull(message, () -> new CourseException(StateEnum.UN_KNOW_EX));

        preService.removeById(courseId); // 删除预发布

    }

    @Override
    public File generateCourseHtml(Long courseId) {
        File htmlFile = null;
        Configuration configuration = new Configuration(Configuration.getVersion());
        try {
            ClassPathResource resource = new ClassPathResource("/templates");
            configuration.setDirectoryForTemplateLoading(resource.getFile());
            configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
            Template template = configuration.getTemplate("course_template.ftl");
            CoursePreviewVO model = this.coursePreview(courseId);
            Map<String, CoursePreviewVO> map = Map.of("model", model);
            String string = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
            htmlFile = File.createTempFile("coursePublish",".html");
            try (InputStream inputStream = IOUtils.toInputStream(string, "utf-8")) {
                IOUtils.copy(inputStream, new FileOutputStream(htmlFile));
            }
        } catch (Exception e) {
            log.error("页面静态化出现问题 courseId:{}",courseId,e);
            e.printStackTrace();
        }
        return htmlFile;
    }

    @Override
    public void uploadCourseHtml(Long courseId, File htmlFile) throws CourseException {
        try {
            MultipartFile file = MultipartSupportConfig.getMultipartFile("file", htmlFile);
            String result = mediaServiceClient.uploadFile(file, "course/" + courseId + ".html", "html");
            if(result == null){
                log.error("远程调用服务降级，上传结构为null,课程id:{},",courseId);
                throw new CourseException(CourseStateEnum.UPLOAD_HTML_ERROR);
            }
        } catch (Exception e) {
            throw new CourseException(e,CourseStateEnum.UPLOAD_HTML_ERROR);
        }
    }

}
