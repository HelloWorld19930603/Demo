package om.yg.shop.server.pojo.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;

/**
 * 定时贴返回dto
 *
 * @author ljh
 * @date 2022/12/9 15:48
 */
@Data
public class FutureArticleDto {
    /**
     * mongodb自生成的主键, ObjectId
     */
    private String id;

    /**
     * 群唯一标识
     */
    private String groupGenUuid;

    /**
     * 帖子内容
     */
    private String content;

    /**
     * 帖子图片路径，hbase路径
     */
    private List<String> mediaFileList;

    /**
     * 发送时间戳(秒)
     */
    private Long sendTime;

    /**
     * 更新时间
     */
    private Long updateTime;

    /**
     * 插入时间
     */
    private Long insertTime;
}
