package com.xyz.oss.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.xyz.oss.config.QiNiuYunProperties;
import com.xyz.oss.service.UploadTokenService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * 七牛云前端上传token
 */
@Service
public class QiNiuYunUploadTokenServiceImpl implements UploadTokenService {
    @Resource
    private QiNiuYunProperties qiNiuYunProperties;
    @Resource(name = "qiNiuYunAuth")
    private Auth auth;
    // token 默认过期时间
    private final long defaultExpireSeconds = 3600;

    /**
     * 简单上传token
     *
     * @return 上传token
     */
    @Override
    public String simpleToken() {
        // 获取token
        return auth.uploadToken(qiNiuYunProperties.getBucket());
    }

    /**
     * 覆盖上传token
     *
     * @param fileName 扩展名的文件名cat.jpg
     * @return 上传token
     */
    @Override
    public String overrideToken(String fileName) {
        // 获取token
        return auth.uploadToken(qiNiuYunProperties.getBucket(), fileName);
    }

    /**
     * 带默认回调参数token
     *
     * @param expireSeconds token 过期时间秒s
     * @return 上传token
     */
    @Override
    public String callbackToken(long expireSeconds) {
        JSONObject returnBody = getReturnBody();
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", returnBody.toString());
        putPolicy.put("callbackBodyType", MediaType.APPLICATION_JSON_VALUE);
        expireSeconds = expireSeconds > 0 ? expireSeconds : defaultExpireSeconds;
        return auth.uploadToken(qiNiuYunProperties.getBucket(), null, expireSeconds, putPolicy);
    }

    /**
     * 自定义回调参数token
     *
     * @param expireSeconds token 过期时间秒s
     * @param callbackUrl   回调url
     * @param selfParam     自定义回调参数 key:value如 user:$(x:user)
     * @return 上传token
     */
    @Override
    public String selfCallbackToken(long expireSeconds, String callbackUrl, HashMap<String, String> selfParam) {
        JSONObject returnBody = getReturnBody();
        returnBody.putAll(selfParam);
        StringMap putPolicy = new StringMap();
        putPolicy.put("callbackUrl", callbackUrl);
        putPolicy.put("returnBody", returnBody.toString());
        putPolicy.put("callbackBodyType", MediaType.APPLICATION_JSON_VALUE);
        expireSeconds = expireSeconds > 0 ? expireSeconds : defaultExpireSeconds;
        // strict false允许添加额外参数
        return auth.uploadToken(qiNiuYunProperties.getBucket(), null, expireSeconds, putPolicy, false);
    }

    /**
     * 七牛云回调公共参数可按需添加
     * https://developer.qiniu.com/kodo/1235/vars#magicvar
     */
    private JSONObject getReturnBody() {
        return JSONUtil.createObj()
                // 文件名
                .set("key", "$(key)")
                // hash值
                .set("hash", "$(etag)")
                // 桶
                .set("bucket", "$(bucket)")
                // 原始文件名
                .set("fname", "$(fname)")
                // 文件大小
                .set("fsize", "$(fsize)")
                // 资源类型
                .set("mimeType", "$(mimeType)")
                // 文件后缀名
                .set("ext", "$(ext)")
                ;
    }
}
