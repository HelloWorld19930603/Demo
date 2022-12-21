package om.yg.shop.server.pojo.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 添加或删除群组标签参数
 *
 * @author ljh
 * @date 2022/11/23 10:23
 */
@Data
public class AddOrDelGroupTag {
    /**
     * 网页用户id
     */
    @NotNull
    private String webUserId;
    /**
     * 网页用户名称,做日志用
     */
    @NotNull
    private String webUserName;
    /**
     * 群的唯一标示,生成的
     */
    private String groupGenUuid;
    /**
     * 群的url,groupGenUuid和groupUrl两个传一个,新群传url,老群传uuid,两个字段不能同时有值
     */
    private String groupUrl;
    /**
     * 标签列表,所有的操作都记录为修改
     */
    @NotNull
    private List<String> tagList;

}
