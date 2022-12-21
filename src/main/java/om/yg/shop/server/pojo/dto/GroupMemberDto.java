package om.yg.shop.server.pojo.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * 群成员返回
 *
 * @author ljh
 * @date 2022/11/23 10:28
 */
@Data
public class GroupMemberDto {
    /**
     * 群唯一id,生成的
     */
    private String groupGenUuid;
    /**
     * 用户唯一id
     */
    private String userGenUuid;
    /**
     * 类型，1-管理员 2-版主 3-专家 4-普通成员
     */
    private Integer memberType;
    /**
     * 类型，1-管理员 2-版主 3-专家 4-普通成员
     */
    private String memberTypeText;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像文件路径， string
     */
    private String headPhoto;
    /**
     * 入群时间
     */
    private Long joinTime;
    /**
     * 由 Workout Partner & Live 邀请
     */
    private String joinGroupType;
    /**
     * 其他一些介绍的信息
     */
    private String otherInfo;
    /**
     * 插入时间
     */
    private Integer insertTime;
    /**
     * 修改时间
     */
    private Integer updateTime;
    /**
     * 标签列表
     */
    private Set<String> tagList;
}
