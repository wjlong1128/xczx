package com.wjl.xczx.media.utils;

import com.wjl.xczx.media.exception.MediaException;
import com.wjl.xczx.media.exception.state.MediaStateEnum;
import lombok.experimental.UtilityClass;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/16
 * @description
 */
@UtilityClass
public class MediaFileUtils {

    public static final String pattern = "yyyy/MM/dd/";

    public static String getDatePath() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String getDatePathFileName(String name) {
        if (name == null || "".equals(name)) {
            throw new MediaException(MediaStateEnum.FILE_NAME_ERROR);
        }
        if (name.startsWith("/")) {
            name = name.substring(0);
        }
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern)) + name;
    }

    public static String suffix(String name){
        if (name == null || "".equals(name)) {
            throw new MediaException(MediaStateEnum.FILE_NAME_ERROR);
        }
        int i = name.lastIndexOf(".");
        if (i == -1){
            return name;
        }
        return name.substring(i);
    }

    public static String fileMD5(File file) throws MediaException{
        try (FileInputStream is = new FileInputStream(file)){
            String md5Hex = DigestUtils.md5Hex(is);
            return md5Hex;
        } catch (IOException e) {
           throw new MediaException(e,MediaStateEnum.FILE_NOT_FOUNT);
        }
    }
}
