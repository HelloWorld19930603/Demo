package com.yg.shop.server.pojo.dto;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;

/**
 * 群成员标签
 *
 * @author ljh
 * @date 2022/11/23 10:28
 */
@Data
public class GroupMemberTagDto {

    /**
     * 生成的唯一标示
     */
    private String userGenUuid;
    /**
     * 标签列表
     */
    private List<String> tagList;
    /**
     * 群id列表
     */
    private List<String> groupGenUuid;
    /**
     * 插入时间
     */
    private Integer insertTime;
    /**
     * 修改时间
     */
    private Integer updateTime;
}
