package om.yg.shop.server.entity;

import lombok.Data;

/**
 * 群信息
 *
 * @author ljh
 * @date 2022/12/5 10:39
 */
@Data
public class GroupInfo {
    /**
     * 群id
     */
    private String id;

    /**
     * 群昵称
     */
    private String name;
}
