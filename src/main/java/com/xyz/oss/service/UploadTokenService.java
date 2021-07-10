package com.xyz.oss.service;

import java.util.HashMap;

/**
 * 生成给前端上传的token
 */
public interface UploadTokenService {
    /**
     * 简单上传token
     *
     * @return 上传token
     */
    String simpleToken();

    /**
     * 覆盖上传token
     *
     * @param fileName 扩展名的文件名cat.jpg
     * @return 上传token
     */
    String overrideToken(String fileName);

    /**
     * 带默认回调参数token
     *
     * @param expireSeconds token 过期时间秒s
     * @return 上传token
     */
    String callbackToken(long expireSeconds);

    /**
     * 自定义回调参数token
     *
     * @param expireSeconds token 过期时间秒s
     * @param callbackUrl   回调url
     * @param selfParam     自定义回调参数 key:value如user:$(x:user)
     * @return 上传token
     */
    String selfCallbackToken(long expireSeconds, String callbackUrl, HashMap<String, String> selfParam);
}
