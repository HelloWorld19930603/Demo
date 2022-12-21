package com.yg.shop.server.repository;

import com.yg.shop.server.entity.AutoReplyTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 自动评论 repository
 *
 * @author ljh
 */
@Repository
public interface AutoReplyTemplateRepository extends MongoRepository<AutoReplyTemplate, String> {

    /**
     * 搜索群的自动评论
     *
     * @param groupGenUuid 群id
     * @return 数据列表
     */
    List<AutoReplyTemplate> findByGroupGenUuid(String groupGenUuid);
}
