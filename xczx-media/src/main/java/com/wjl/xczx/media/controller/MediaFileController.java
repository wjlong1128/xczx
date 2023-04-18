package com.wjl.xczx.media.controller;

import com.wjl.xczx.common.result.PageResult;
import com.wjl.xczx.common.result.Result;
import com.wjl.xczx.common.vo.PageParams;
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
        Long companyId = 1232141425L;
        return mediaFileService.queryMediaFiles(companyId,pageParams,queryMediaParamsDTO);
    }

    // TODO 机构id
    @RequestMapping(value = "upload/coursefile",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<MediaFileVO> uploadFile(@RequestPart("file")MultipartFile file){
        Long companyId = 1232141425L;
        return mediaFileService.uploadImageFile(companyId,file);
    }

}
