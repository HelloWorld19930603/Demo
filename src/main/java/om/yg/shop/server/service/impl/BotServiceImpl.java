package om.yg.shop.server.service.impl;

import com.yg.shop.server.constant.*;
import com.yg.shop.server.entity.Group;
import com.yg.shop.server.entity.OperatorLog;
import com.yg.shop.server.pojo.vo.BotHeartVo;
import com.yg.shop.server.pojo.vo.IdVo;
import com.yg.shop.server.pojo.vo.LoginUserInfo;
import com.yg.shop.server.repository.GroupRepository;
import com.yg.shop.server.repository.OperatorLogRepository;
import com.yg.shop.server.service.BotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 机器人接口的实现类
 *
 * @author ljh
 * @date 2022/11/22 11:36
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class BotServiceImpl implements BotService {

    private StringRedisTemplate redisTemplate;
    private GroupRepository groupRepository;
    private MongoTemplate mongoTemplate;
    private OperatorLogRepository operatorLogRepository;

    @Autowired
    public BotServiceImpl(StringRedisTemplate template
            , GroupRepository groupRepository
            , MongoTemplate mongoTemplate
            , OperatorLogRepository operatorLogRepository) {
        this.redisTemplate = template;
        this.groupRepository = groupRepository;
        this.mongoTemplate = mongoTemplate;
        this.operatorLogRepository = operatorLogRepository;
    }


    /**
     * 给采集端提供的接口,作用是将机器人的uuid放入缓存中
     *
     * @param vo 参数
     */
    @Override
    public void botHeart(BotHeartVo vo) {
        String s = redisTemplate.opsForValue().get(SystemConstant.bot + vo.getBotUserGenUuid());
        if (null != s) {
            Integer value = Integer.parseInt(s);
            if (!value.equals(BotStatusEnum.NORMAL.getType())) {
                updateBotStatus(vo.getBotUserGenUuid(), vo.getBotStatus());
            }
        }
        redisTemplate.opsForValue().set(SystemConstant.bot + vo.getBotUserGenUuid(), vo.getBotStatus() + "",
                SystemConstant.botOutTime, TimeUnit.MINUTES);

    }

    @Override
    public Integer autoFabulous(IdVo vo, LoginUserInfo user) {
        try {
            Group group = groupRepository.findById(vo.getId()).get();
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(vo.getId()));
            Update update = new Update();
            Integer autoLike;
            Integer operator;
            if (null == group.getAutoLike()) {
                autoLike = AutoLikeEnum.CLOSE.getType();
                operator = OperatorLogEnum.CLOSE_AUTO_LIKE.getType();
            } else if (group.getAutoLike() == AutoLikeEnum.CLOSE.getType()) {
                autoLike = AutoLikeEnum.OPEN.getType();
                operator = OperatorLogEnum.OPEN_AUTO_LIKE.getType();

            } else {
                autoLike = AutoLikeEnum.CLOSE.getType();
                operator = OperatorLogEnum.CLOSE_AUTO_LIKE.getType();
            }
            update.set("auto_like", autoLike);
            update.set("update_time", System.currentTimeMillis() / 1000);
            mongoTemplate.updateFirst(query, update, "group");
            OperatorLog log = new OperatorLog();
            log.setOperator(operator);
            log.setResult(OperatorLogResultEnum.SUCCESS.getType());
            log.setWebUserId(user.getWebUserId());
            log.setWebUsername(user.getWebUsername());
            log.setInsertTime(System.currentTimeMillis() / 1000);
            log.setUpdateTime(System.currentTimeMillis() / 1000);
            Map<String, String> map = new HashMap<>(16);
            map.put("group_gen_uuid", vo.getGroupGenUuid());
            log.setData(map);
            operatorLogRepository.save(log);
            return autoLike;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Integer autoComment(IdVo vo, LoginUserInfo user) {
        try {
            Group group = groupRepository.findById(vo.getId()).get();
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(vo.getId()));
            Update update = new Update();
            Integer autoReply;
            Integer operator;
            if (null == group.getAutoReply()) {
                autoReply = AutoReplyEnum.CLOSE.getType();
                operator = OperatorLogEnum.CLOSE_AUTO_REPLY.getType();
            } else if (group.getAutoReply() == AutoReplyEnum.CLOSE.getType()) {
                autoReply = AutoReplyEnum.OPEN.getType();
                operator = OperatorLogEnum.OPEN_AUTO_REPLY.getType();
            } else {
                autoReply = AutoReplyEnum.CLOSE.getType();
                operator = OperatorLogEnum.CLOSE_AUTO_REPLY.getType();
            }
            update.set("auto_reply", autoReply);
            update.set("update_time", System.currentTimeMillis() / 1000);
            mongoTemplate.updateFirst(query, update, "group");
            OperatorLog log = new OperatorLog();
            log.setOperator(operator);
            log.setResult(OperatorLogResultEnum.SUCCESS.getType());
            log.setWebUserId(user.getWebUserId());
            log.setWebUsername(user.getWebUsername());
            log.setInsertTime(System.currentTimeMillis() / 1000);
            log.setUpdateTime(System.currentTimeMillis() / 1000);
            Map<String, String> map = new HashMap<>(16);
            map.put("group_gen_uuid", vo.getGroupGenUuid());
            log.setData(map);
            operatorLogRepository.save(log);
            return autoReply;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 检查机器人的心跳,五分钟检查一次,一次检查所有的机器人
     **/
    @Scheduled(cron = "0 0/5 * * * ? ")
    public void checkHeartbeat() {
        List<Group> all = groupRepository.findAll();
        Set<String> uuidSet = all.stream().map(Group::getBotUserGenUuid)
                .filter(Objects::nonNull).collect(Collectors.toSet());
        for (String botUuid : uuidSet) {
            String s = redisTemplate.opsForValue().get(SystemConstant.bot + botUuid);
            if (null == s) {
                updateBotStatus(botUuid, BotStatusEnum.ABNORMAL.getType());
            }
        }
    }

    /**
     * 修改机器人的状态
     *
     * @param botUuid 参数 机器人id
     * @param status  参数 状态
     */
    public void updateBotStatus(String botUuid, Integer status) {
        if (botUuid == null) {
            return;
        }
        botUuid = botUuid.replace(SystemConstant.bot, "");
        List<Group> list = groupRepository.findByBotUserGenUuid(botUuid);
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        for (Group group : list) {
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(group.getId()));
            Update update = new Update();
            update.set("update_time", System.currentTimeMillis() / 1000);
            update.set("bot_status", status);
            mongoTemplate.updateFirst(query, update, "group");
        }

    }
}
