package com.wjl.xczx.content.course.service;

import com.wjl.xczx.content.course.model.vo.CoursePreviewVO;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/19
 * @description
 */
@SpringBootTest
public class TestFreemarket {

    @Autowired
    CoursePublishService publishService;

    @Test
    void testGeneraterHtml() throws IOException, TemplateException {
        CoursePreviewVO model = publishService.coursePreview(117L);

        Configuration configuration = new Configuration(Configuration.getVersion());
        ClassPathResource resource = new ClassPathResource("/templates");
        configuration.setDirectoryForTemplateLoading(resource.getFile());
        configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
        Template template = configuration.getTemplate("course_template.ftl");
        Map<String, CoursePreviewVO> map = Map.of("model", model);
        String string = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        System.out.println(string);

        InputStream inputStream = IOUtils.toInputStream(string,"utf-8");
        IOUtils.copy(inputStream,new FileOutputStream(resource.getPath() + "abc.html"));    }
}
