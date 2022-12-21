package om.yg.shop.server.pojo.dto;

import com.yg.shop.server.entity.GroupInfo;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 入群申请
 *
 * @author ljh
 * @date 2022/11/22 11:47
 */
@Data
public class JoinGroupRequestDto {
    /**
     * 生成的唯一标示
     */
    private String groupGenUuid;
    /**
     * 生成的用户id(账号)
     */
    private String userGenUuid;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像url
     */
    private String headPhotoUrl;
    /**
     * 头像文件路径
     */
    private String headPhoto;
    /**
     * 加入fb时间
     */
    private Long joinTime;
    /**
     * 申请时间
     */
    private Long requestTime;
    /**
     * 接受或拒绝时间
     */
    private Long operatorTime;
    /**
     * 请求问题及答案列表
     */
    private List<Map<String, String>> questionList;
    /**
     * 好友数
     */
    private Integer friendCount;
    /**
     * 群内好友数
     */
    private Integer groupFriendCount;
    /**
     * 加群数
     */
    private Integer groupCount;
    /**
     * 群昵称之类
     */
    private List<GroupInfo> openGroupsList;
    /**
     * 邀请人id，谁邀请改用户入群, 如果是自己加入，邀请人是自己
     */
    private String inviteUserId;
    /**
     * 邀请人id，谁邀请改用户入群, 如果是自己加入，邀请人是自己
     */
    private String inviteUserGenUuid;
    /**
     * 邀请人昵称
     */
    private String inviteNickname;
    /**
     * 所在位置
     */
    private String location;
    /**
     * 教育
     */
    private String education;
    /**
     * 工作
     */
    private String work;
    /**
     * 0-待操作 1-接受 2-拒绝 3-接受处理 4-拒绝处理
     */
    private Integer status;
    /**
     * 插入时间
     */
    private Long insertTime;
    /**
     * 修改时间
     */
    private Long updateTime;
    /**
     * 标签列表
     */
    private Set<String> tagList;
}
