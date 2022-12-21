package com.yg.shop.server.constant;

import lombok.Data;

/**
 * 系统常量
 *
 * @author ljh
 * @date 2022/11/28 14:35
 */
@Data
public class SystemConstant {
    /**
     * 下载地址
     */
    public final static String hBaseServer = "http://192.168.2.4:10908";

    /**
     * 下载接口
     */
    public final static String hBaseUpLoadUrl = "/file_system/download/";

    /**
     * 替换内容
     */
    public final static String replace = "/";

    /**
     * 机器人前缀
     */
    public final static String bot = "botHeartInfo:";

    /**
     * 机器人超时时间
     */
    public final static Long botOutTime = 5L;

    /**
     * 导出成员的url
     */
    public final static String importMemberUrl = "https://www.facebook.com/";

    /**
     * 图片访问路径
     */
//    public final static String imgPath = "http://192.168.2.5:13679/";

    /**
     * 图片上传路径
     */
    public final static String uploadUrl = "http://192.168.2.4:10908/file_system/upload/";

    /**
     * 电商服务
     */
    public final static String shop = "shop";
}
