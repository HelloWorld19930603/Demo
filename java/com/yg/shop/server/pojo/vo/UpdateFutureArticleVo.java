package com.yg.shop.server.pojo.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 修改定时贴
 *
 * @author ljh
 * @date 2022/12/9 15:57
 */
@Data
public class UpdateFutureArticleVo {
    /**
     * 帖子id
     */
    @NotNull
    private String id;

    /**
     * 发帖时间
     */
    @NotNull
    private Long sendTime;

    /**
     * 群id
     */
    private String groupGenUuid;

    /**
     * 帖子内容
     */
    private String content;

    /**
     * 帖子图片
     */
    private List<MultipartFile> mediaFileList;

    /**
     * 需要保存的图片,有路径的,新增的文件放到  mediaFileList里面
     */
    private List<String> saveImgList;

}
