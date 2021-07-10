package com.xyz.oss.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 七牛云配置
 */
@Component
@ConfigurationProperties(prefix = "com.qiniu")
public class QiNiuYunProperties {
    /**
     * ID
     */
    private String accessKeyId;
    /**
     * 秘钥
     */
    private String accessSecretId;
    /**
     * 桶
     */
    private String bucket;
    /**
     * 访问文件的域名
     */
    private String fileDomain;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessSecretId() {
        return accessSecretId;
    }

    public void setAccessSecretId(String accessSecretId) {
        this.accessSecretId = accessSecretId;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getFileDomain() {
        return fileDomain;
    }

    public void setFileDomain(String fileDomain) {
        this.fileDomain = fileDomain;
    }
}
