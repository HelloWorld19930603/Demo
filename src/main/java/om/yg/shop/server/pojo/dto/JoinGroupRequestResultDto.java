package om.yg.shop.server.pojo.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 入群申请返回实体类
 *
 * @author ljh
 * @date 2022/11/30 16:28
 */
@Data
public class JoinGroupRequestResultDto {
    /**
     * 数据列表
     */
    private List<JoinGroupRequestDto> list = new ArrayList<>();
    /**
     * 申请数量
     */
    private Integer applyCount = 0;
    /**
     * 处理数量
     */
    private Integer handleCount = 0;
    /**
     * 未处理数量
     */
    private Integer NoHandleCount = 0;
    /**
     * 列表数量
     */
    private Integer count = 0;
}
