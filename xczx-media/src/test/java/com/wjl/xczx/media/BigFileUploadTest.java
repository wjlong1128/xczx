package com.wjl.xczx.media;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @date 2023/4/16
 * @description
 */
@SpringBootTest
public class BigFileUploadTest {

    public static final String PATH = "D:\\download\\douyin.mp4";
    public static final String TARGET_PATH = "C:\\Users\\wangj\\Desktop\\chunk\\";
    public static final int SIZE = 1024 * 1024 * 1;

    @Test
    void testRandomChunk() throws IOException {
        File sourceFile = new File(PATH);
        double num = Math.ceil((double) sourceFile.length() / SIZE);
        RandomAccessFile readAs = new RandomAccessFile(sourceFile, "r");
        for (int i = 0; i < num; i++) {
            File file = new File(TARGET_PATH + i);
            RandomAccessFile rw = new RandomAccessFile(file, "rw");
            byte[] buff = new byte[1024];
            int len = -1;
            while ((len = readAs.read(buff)) != -1) {
                rw.write(buff, 0, len);
                if (file.length() >= SIZE) {
                    break;
                }
            }
            rw.close();
        }
        readAs.close();
    }

    @Test
    void testRandomMerge() throws IOException {
        File chunkFolder = new File(TARGET_PATH);
        File sourceFile = new File(PATH);
        File targetFile = new File("target.mp4");

        List<File> files = Arrays
                .stream(chunkFolder.listFiles())
                .sorted(Comparator.comparingLong(a -> Long.parseLong(a.getName())))
                .collect(Collectors.toList());
        System.out.println(files);
        RandomAccessFile target = new RandomAccessFile(targetFile, "rw");

        for (File file : files) {
            RandomAccessFile r = new RandomAccessFile(file, "r");
            int len = -1;
            byte[] buf = new byte[1024];
            while ((len = r.read(buf)) != -1) {
                target.write(buf, 0, len);
            }
            r.close();
        }
        target.close();

        FileInputStream i1 = new FileInputStream(sourceFile);
        FileInputStream i2 = new FileInputStream(targetFile);

        System.out.println(DigestUtils.md5Hex(i1).equals(DigestUtils.md5Hex(i2)));
        System.out.println(org.springframework.util.DigestUtils.md5DigestAsHex(i1).equals(org.springframework.util.DigestUtils.md5DigestAsHex(i2)));
        i1.close();
        i2.close();
    }
}
