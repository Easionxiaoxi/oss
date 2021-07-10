package com.xyz.oss.service;


import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
class UploadServiceTest extends TestCase {

    @Resource(name = "qiNiuYunUploadServiceImpl")
    private UploadService uploadService;

    @Test
    public void upload() throws Exception {
        InputStream inputStream = new FileInputStream("C:\\Users\\Administrator\\Desktop\\cat.jpg");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int ch;
        while ((ch = inputStream.read()) != -1) {
            outputStream.write(ch);
        }
        String url = uploadService.upload(outputStream.toByteArray(), UUID.randomUUID().toString() + ".jpg");
        System.out.printf("上传结果url = %s%n", url);
    }

    @Test
    public void testUpload() {
        String url = uploadService.upload("C:\\Users\\Administrator\\Desktop\\cat.jpg", UUID.randomUUID().toString() + ".jpg");
        System.out.printf("上传结果url = %s%n", url);
    }
}