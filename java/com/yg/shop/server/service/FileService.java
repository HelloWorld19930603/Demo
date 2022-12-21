package com.yg.shop.server.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件service
 *
 * @author ljh
 * @date 2022/12/9 17:01
 */
public interface FileService {


    /**
     * 上传文件
     *
     * @param url      上传路径    SystemConstant.uploadUrl+/群id/文件名
     * @param fileName 文件名
     * @param file     文件
     * @return 图片访问的路径，存数据库中
     */
    String upload(String url, String fileName, MultipartFile file);
}
