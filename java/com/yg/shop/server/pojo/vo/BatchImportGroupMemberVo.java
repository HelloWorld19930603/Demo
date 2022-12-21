package com.yg.shop.server.pojo.vo;

import com.yg.shop.server.pojo.dto.GroupMemberDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 批量导出群成员信息
 *
 * @author ljh
 * @date 2022/11/23 11:20
 */
@Data
public class BatchImportGroupMemberVo {
    /**
     * 数据列表
     */
    private List<GroupMemberDto> list;

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
     * 全部导出传群id,批量导出就不传
     */
    private String groupGenUuid;

    /**
     * 群昵称
     */
    @NotNull
    private String groupNickname;

    /**
     * 群成员搜索条件
     */
    private GroupMemberVo vo;
}
