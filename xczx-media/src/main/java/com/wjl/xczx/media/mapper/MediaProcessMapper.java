package com.wjl.xczx.media.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wjl.xczx.media.model.entity.MediaProcess;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/17
 * @description
 */
public interface MediaProcessMapper extends BaseMapper<MediaProcess> {
    /**
     *  根据当前任务分片获取任务
     * @param shardIndex
     * @param shardTotal
     * @param count
     * @return
     */
    @Select("select * from media_process p where p.id%#{total} = #{index} and (p.status = '1' or p.status = '3') and p.fail_count < 3 limit #{count}")
    List<MediaProcess> getPendingTasks(@Param("index") int shardIndex, @Param("total") int shardTotal, @Param("count") int count);

    /**
     *  开启一个任务
     * @param taskId
     * @return
     */
    @Update(""" 
                update 
                    media_process 
                set 
                    status = '4' 
                where id = #{id} 
                and (status = '1' or status = '3') 
                and fail_count < 3
            """)
    int startTask(@Param("id") Long taskId);

}

