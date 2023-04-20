package com.wjl.xczx.media.api;

import com.wjl.xczx.common.result.PageResult;
import com.wjl.xczx.common.result.Result;
import com.wjl.xczx.common.vo.PageParams;
import com.wjl.xczx.media.GlobalConst;
import com.wjl.xczx.media.model.dto.QueryMediaParamsDTO;
import com.wjl.xczx.media.model.vo.MediaFileVO;
import com.wjl.xczx.media.service.MediaFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/16
 * @description
 */
@RequestMapping
@RestController
@RequiredArgsConstructor
public class MediaFileController {

    private final MediaFileService mediaFileService;
    @PostMapping("files")
    public Result<PageResult<MediaFileVO>> list(PageParams pageParams, @RequestBody QueryMediaParamsDTO queryMediaParamsDTO){
        Long companyId = GlobalConst.COMPANY_ID;
        return mediaFileService.queryMediaFiles(companyId,pageParams,queryMediaParamsDTO);
    }


    @RequestMapping(value = "upload/coursefile",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<MediaFileVO> uploadFile(@RequestPart("file") MultipartFile file,@RequestParam(value="objectName",required = false)String objectName,@RequestParam(value="tags",required = false)String tags){
       Long companyId = GlobalConst.COMPANY_ID;
        return mediaFileService.uploadFile(companyId,file,objectName,tags);
    }



    @DeleteMapping("{fileId}")
    public Result deleteFile(@PathVariable("fileId") String fileId){
        Long companyId = GlobalConst.COMPANY_ID;
        return mediaFileService.deleteFileById(companyId,fileId);
    }

}
