package com.xyz.oss.config;

import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 七牛云文件上传配置
 */
@Configuration
public class QiNiuYunConfig {
    @Resource
    private QiNiuYunProperties qiNiuYunProperties;

    @Bean(name = "qiNiuYunConfiguration")
    public com.qiniu.storage.Configuration initConfiguration() {
        //构造一个带指定 Region 对象的配置类
        return new com.qiniu.storage.Configuration(Zone.huanan());
    }

    @Bean(name = "qiNiuYunAuth")
    public Auth initAuth() {
        return Auth.create(qiNiuYunProperties.getAccessKeyId(), qiNiuYunProperties.getAccessSecretId());
    }

    @Bean
    public BucketManager initBucketManager(@Qualifier("qiNiuYunAuth") Auth auth, @Qualifier("qiNiuYunConfiguration") com.qiniu.storage.Configuration config) {
        return new BucketManager(auth, config);
    }

    @Bean
    public UploadManager initUploadManager(@Qualifier("qiNiuYunConfiguration") com.qiniu.storage.Configuration config) {
        UploadManager uploadManager = new UploadManager(config);
        return uploadManager;
    }
}
