package com.wjl.xczx.media.api;

import com.wjl.xczx.common.result.RestResponse;
import com.wjl.xczx.common.result.Result;
import com.wjl.xczx.media.service.MediaFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/18
 * @description
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("open")
public class MediaOpenController {

    private final MediaFileService mediaFileService;

    @GetMapping("preview/{mediaId}")
    public RestResponse<String> getPlayUrlByMediaId(@PathVariable("mediaId") String mediaId){
        return mediaFileService.getFileUrl(mediaId);
    }

}
