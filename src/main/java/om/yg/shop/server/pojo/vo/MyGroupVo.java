package om.yg.shop.server.pojo.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 我的群组搜索
 *
 * @author ljh
 * @date 2022/11/22 17:01
 */
@Data
public class MyGroupVo {
    /**
     * 网页用户id
     */
    @NotNull
    private String webUserId;
    /**
     * 当前页
     */
    @NotNull
    private Integer pageNum;
    /**
     * 每页显示的数量
     */
    @NotNull
    private Integer pageSize;
    /**
     * 搜索文本
     */
    private String text;
}
