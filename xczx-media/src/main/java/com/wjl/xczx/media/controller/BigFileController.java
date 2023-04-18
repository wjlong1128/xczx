package com.wjl.xczx.media.controller;

import com.wjl.xczx.common.result.Result;
import com.wjl.xczx.media.service.MediaFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/16
 * @description
 */
@RequestMapping("upload")
@RestController
@RequiredArgsConstructor
public class BigFileController {

    private final MediaFileService mediaFileService;

    @PostMapping("checkfile")
    public Result<Boolean> checkFile(
            @RequestParam("fileMd5") String fileMd5
    ) {
        return mediaFileService.checkFile(fileMd5);
    }


    @PostMapping("checkchunk")
    public Result<Boolean> checkchunk(
            @RequestParam("fileMd5") String fileMd5,
            @RequestParam("chunk") int chunk
    ) {
        return mediaFileService.checkChunkFile(fileMd5,chunk);
    }

    @PostMapping("uploadchunk")
    public Result<Boolean> uploadChunk(
            @RequestParam("file") MultipartFile file,
            @RequestParam("fileMd5") String fileMd5,
            @RequestParam("chunk") Integer chunk
    ) throws IOException {
        return mediaFileService.uploadChunk(file,fileMd5,chunk);
    }

    @PostMapping("mergechunks")
    public Result checkChunkFile(
            @RequestParam("fileMd5") String fileMd5,
            @RequestParam("fileName") String fileName,
            @RequestParam("chunkTotal") Integer chunkTotal
    ) {
        // TODO 课程id
        Long companyId = 1232141425L;
        //FileInfoDTO infoDTO = new FileInfoDTO().setTags("视频文件").setFileType(MediaFile.VIDEO).setFilename(fileName);
        return mediaFileService.mergeChunks(companyId,fileMd5,fileName,chunkTotal);
    }

}
