package om.yg.shop.server.pojo.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 发帖参数
 *
 * @author ljh
 * @date 2022/12/12 17:13
 */
@Data
public class SendArticleVo {
    /**
     * 文章内容
     */
    @NotNull
    private String content;

    /**
     * 图片列表
     */
    private List<MultipartFile> fileList;

    /**
     * 群唯一id
     */
    @NotNull
    private String groupGenUuid;

    /**
     * 文章作者id(机器人)
     */
    @NotNull
    private String userGenUuid;

    /**
     * 发送时间戳(秒)
     */
    @NotNull
    private Long sendTime;
}
