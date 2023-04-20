package com.wjl.xczx.content.course.api;

import com.wjl.xczx.common.result.Result;
import com.wjl.xczx.content.course.model.dto.BindTeachPlanMediaDTO;
import com.wjl.xczx.content.course.service.TeachplanMediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/18
 * @description
 */
@RequiredArgsConstructor
@RequestMapping("teachplan")
@RestController
public class TeachplanMediaController {

    private final TeachplanMediaService teachplanMediaService;

    /**
     *  课程计划视频与媒资绑定
     * @param bindTeachPlanMediaDTO
     */
    @PostMapping("association/media")
    public Result associationMedia(@RequestBody BindTeachPlanMediaDTO bindTeachPlanMediaDTO){
        teachplanMediaService.associationMedia(bindTeachPlanMediaDTO);
        return Result.success();
    }

    @DeleteMapping("association/media/{teachId}/{mediaId}")
    public Result unAssociationMedia(@PathVariable("teachId") Long teachplanId,@PathVariable("mediaId") String mediaId){
        return teachplanMediaService.unAssociationMedia(teachplanId,mediaId);
    }

}
