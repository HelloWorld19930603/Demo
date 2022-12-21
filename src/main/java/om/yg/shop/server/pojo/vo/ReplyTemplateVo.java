package om.yg.shop.server.pojo.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 添加自动评论
 *
 * @author ljh
 * @date 2022/12/9 16:15
 */
@Data
public class ReplyTemplateVo {
    /**
     * id
     */
    private String id;

    /**
     * 群唯一标识
     */
    @NotNull
    private String groupGenUuid;

    /**
     * 关键词列表
     */
    @NotNull
    private List<String> keywordList;

    /**
     * 删除的图片路径
     */
    private String delImgPath;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 图片
     */
    private MultipartFile file;

    /**
     * 是否设置为默认评论, 0-不是  1-是，默认为0
     */
    private Integer isDefault=0;

    /**
     * 自动评论的数量
     */
    private Integer replyNum=3;

}
