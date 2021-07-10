package com.xyz.oss.service.impl;

import cn.hutool.json.JSONUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.xyz.oss.config.QiNiuYunProperties;
import com.xyz.oss.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.net.URL;
import java.net.URLConnection;

/**
 * 七牛云文件上传
 */
@Service(value = "qiNiuYunUploadServiceImpl")
public class QiNiuYunUploadServiceImpl implements UploadService {
    @Resource
    private QiNiuYunProperties qiNiuYunProperties;
    @Resource(name = "qiNiuYunAuth")
    private Auth auth;
    @Autowired
    private UploadManager uploadManager;

    /**
     * 文件上传
     *
     * @param data     字节数组
     * @param fileName 文件名
     * @return 文件url
     */
    @Override
    public String upload(byte[] data, String fileName) {
        // 文件存在则返回url
        boolean exist = isFileExist(qiNiuYunProperties.getFileDomain() + fileName);
        if (exist) {
            return qiNiuYunProperties.getFileDomain() + fileName;
        }
        try {
            // 获取token
            String token = auth.uploadToken(qiNiuYunProperties.getBucket());
            // 上传
            Response response = uploadManager.put(data, fileName, token);
            // 获取上传文件url
            if (response.isOK()) {
                DefaultPutRet defaultPutRet = JSONUtil.toBean(response.bodyString(), DefaultPutRet.class);
                return qiNiuYunProperties.getFileDomain() + defaultPutRet.key;
            }
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 文件上传
     *
     * @param filePath 文件路径
     * @param fileName 文件名
     * @return 文件url
     */
    @Override
    public String upload(String filePath, String fileName) {
        // 文件存在则返回url
        boolean exist = isFileExist(qiNiuYunProperties.getFileDomain() + fileName);
        if (exist) {
            return qiNiuYunProperties.getFileDomain() + fileName;
        }
        try {
            // 获取token
            String token = auth.uploadToken(qiNiuYunProperties.getBucket());
            // 上传
            Response response = uploadManager.put(filePath, fileName, token);
            // 获取上传文件url
            if (response.isOK()) {
                DefaultPutRet defaultPutRet = JSONUtil.toBean(response.bodyString(), DefaultPutRet.class);
                return qiNiuYunProperties.getFileDomain() + defaultPutRet.key;
            }
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 文件是否存在
     *
     * @param urlName 文件url
     * @return boolean
     */
    private boolean isFileExist(String urlName) {
        try {
            URL url = new URL(urlName);
            URLConnection connection = url.openConnection();
            if (!ObjectUtils.isEmpty(connection.getInputStream())) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
