package com.yg.shop.server.pojo.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 创建定时贴
 *
 * @author ljh
 * @date 2022/12/9 15:36
 */
@Data
public class AddFutureArticleVo {
    /**
     * 群唯一标识
     */
    @NotNull
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
     * 发送时间戳(秒)
     */
    @NotNull
    private Long sendTime;

    /**
     * 文章作者id(机器人)
     */
    @NotNull
    private String userGenUuid;
}
