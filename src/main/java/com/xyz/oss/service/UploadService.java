package com.xyz.oss.service;

/**
 * 文件上传
 */
public interface UploadService {
    /**
     * 文件上传
     *
     * @param data     字节数组
     * @param fileName 文件名
     * @return 文件url
     */
    String upload(byte[] data, String fileName);

    /**
     * 文件上传
     *
     * @param filePath 文件路径
     * @param fileName 文件名
     * @return 文件url
     */
    String upload(String filePath, String fileName);
}
