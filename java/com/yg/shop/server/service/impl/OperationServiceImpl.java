package com.yg.shop.server.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.yg.shop.server.constant.*;
import com.yg.shop.server.entity.*;
import com.yg.shop.server.pojo.vo.OperatorVo;
import com.yg.shop.server.repository.*;
import com.yg.shop.server.service.OperationService;
import com.yg.shop.server.pojo.vo.OperatorVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 操作接口的实现类
 *
 * @author ljh
 * @date 2022/11/22 11:36
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private OperatorInfoRepository operatorInfoRepository;
    private OperatorLogRepository operatorLogRepository;
    private JoinGroupRequestRepository joinGroupRequestRepository;
    private InviteInfoRepository inviteInfoRepository;
    private MongoTemplate mongoTemplate;
    private final long deadline = 60 * 60 * 12;

    @Autowired
    public OperationServiceImpl(OperatorInfoRepository operatorInfoRepository
            , OperatorLogRepository operatorLogRepository
            , JoinGroupRequestRepository joinGroupRequestRepository
            , InviteInfoRepository inviteInfoRepository
            , MongoTemplate mongoTemplate) {
        this.operatorInfoRepository = operatorInfoRepository;
        this.operatorLogRepository = operatorLogRepository;
        this.joinGroupRequestRepository = joinGroupRequestRepository;
        this.inviteInfoRepository = inviteInfoRepository;
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 保存操作
     *
     * @param vo 参数
     * @return boolean
     */
    @Override
    public Boolean saveOperator(OperatorVo vo) {
        try {
            switch (vo.getOperator()) {
                case 1:
                    batchAgree(vo);
                    break;
                case 2:
                    batchRefuse(vo);
                    break;
                case 3:
                    batchRemind(vo);
                    break;
                case 4:
                    batchRemove(vo);
                    break;
                case 5:
                    allRemind(vo);
                    break;
                default:
                    System.out.println("---------------------华丽的分割线--------------------");
            }
            String operatorId = saveOperatorInfo(vo);
            saveOperatorLog(vo, operatorId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 全部提醒
     *
     * @param vo 参数
     */
    private void allRemind(OperatorVo vo) {
        List<InviteInfo> list = inviteInfoRepository
                .findByGroupGenUuidAndStatus(vo.getGroupGenUuid(), InviteStatusEnum.NOT_RECEIVE.getType());
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        long end = System.currentTimeMillis() / 1000;
        ArrayList<String> userIds = new ArrayList<>(10);
        List<InviteInfo> collect = list.stream().filter(i -> (end - i.getSendInviteTime() > deadline)).map(i -> {
            Integer sendInviteCount = i.getSendInviteCount();
            sendInviteCount++;
            HashMap<String, String> map = new HashMap<>();
            map.put("_id", i.getId());
            map.put("collectionName", "invite_info");
            map.put("sendInviteCount", sendInviteCount + "");
            updateSendInviteCount(map);
            userIds.add(i.getUserGenUuid());
            return i;
        }).collect(Collectors.toList());
        vo.setUserIdList(userIds);
        log.info("全部提醒的人数{}", collect.size());
    }


    /**
     * 批量提醒
     *
     * @param vo 参数
     */
    private void batchRemind(OperatorVo vo) {
        List<InviteInfo> list = inviteInfoRepository.findAllByUserGenUuidIn(vo.getUserIdList());
        ArrayList<String> userIds = new ArrayList<>(10);
        long end = System.currentTimeMillis() / 1000;
        for (InviteInfo inviteInfo : list) {
            Integer sendInviteCount = inviteInfo.getSendInviteCount();
            sendInviteCount++;
            HashMap<String, String> map = new HashMap<>();
            map.put("_id", inviteInfo.getId());
            map.put("collectionName", "invite_info");
            map.put("sendInviteCount", sendInviteCount + "");
            if ((end - inviteInfo.getSendInviteTime()) > deadline) {
                updateSendInviteCount(map);
                userIds.add(inviteInfo.getUserGenUuid());
            }
        }
        vo.setUserIdList(userIds);
    }

    /**
     * 修改页面提醒时间
     *
     * @param map 参数
     */
    private void updateSendInviteCount(HashMap<String, String> map) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(map.get("_id")));
        Update update = new Update();
        update.set("send_invite_count", Integer.parseInt(map.get("sendInviteCount")));
        update.set("update_time", System.currentTimeMillis() / 1000);
        update.set("web_remind_time", System.currentTimeMillis() / 1000);
        update.set("send_invite_time", System.currentTimeMillis() / 1000);
        mongoTemplate.updateFirst(query, update, map.get("collectionName"));
    }

    /**
     * 批量移除
     *
     * @param vo 参数
     */
    private void batchRemove(OperatorVo vo) {
        List<InviteInfo> list = inviteInfoRepository.findAllByUserGenUuidIn(vo.getUserIdList());
        for (InviteInfo inviteInfo : list) {
            HashMap<String, String> map = new HashMap<>();
            map.put("_id", inviteInfo.getId());
            map.put("status", InviteStatusEnum.REMOVE.getType() + "");
            map.put("collectionName", "invite_info");
            updateCollection(map);
        }
    }

    /**
     * 更加数据的状态
     *
     * @param map 参数
     */
    private void updateCollection(Map<String, String> map) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(map.get("_id")));
        Update update = new Update();
        update.set("status", Integer.parseInt(map.get("status")));
        update.set("update_time", System.currentTimeMillis() / 1000);
        mongoTemplate.updateFirst(query, update, map.get("collectionName"));
    }

    /**
     * 批量拒绝
     *
     * @param vo 参数
     */
    private void batchRefuse(OperatorVo vo) {
        List<JoinGroupRequest> list = joinGroupRequestRepository.findAllByUserGenUuidIn(vo.getUserIdList());
        for (JoinGroupRequest joinGroupRequest : list) {
            HashMap<String, String> map = new HashMap<>();
            map.put("_id", joinGroupRequest.getId());
            map.put("status", JoinGroupRequestStatusEnum.REFUSE_PROCESSING.getType() + "");
            map.put("collectionName", "join_group_request");
            updateCollection(map);

        }
    }

    /**
     * 批量同意
     *
     * @param vo 参数
     */
    private void batchAgree(OperatorVo vo) {
        List<JoinGroupRequest> list = joinGroupRequestRepository.findAllByUserGenUuidIn(vo.getUserIdList());
        for (JoinGroupRequest joinGroupRequest : list) {
            HashMap<String, String> map = new HashMap<>();
            map.put("_id", joinGroupRequest.getId());
            map.put("status", JoinGroupRequestStatusEnum.RECEIVE_PROCESSING.getType() + "");
            map.put("collectionName", "join_group_request");
            updateCollection(map);
        }
    }

    /**
     * 保存操作日志
     *
     * @param vo         网页参数
     * @param operatorId 操作日志id
     */
    private void saveOperatorLog(OperatorVo vo, String operatorId) {
        OperatorLog entity = new OperatorLog();
        entity.setInsertTime(System.currentTimeMillis() / 1000);
        entity.setUpdateTime(System.currentTimeMillis() / 1000);
        entity.setWebUserId(vo.getWebUserId());
        entity.setWebUsername(vo.getWebUserName());
        entity.setOperator(vo.getLogStatus());
        entity.setOperatorInfoId(operatorId);
        entity.setResult(OperatorLogResultEnum.NOT_START.getType());
        HashMap<String, String> map = new HashMap<>(16);
        map.put("group_gen_uuid", vo.getGroupGenUuid());
        map.put("user_gen_uuid_list", JSONArray.toJSONString(vo.getUserIdList()));
        entity.setData(map);
        if (!StringUtils.isEmpty(operatorId)) {
            operatorLogRepository.save(entity);
        }
    }

    /**
     * 保存操作
     *
     * @param vo 参数信息
     */
    private String saveOperatorInfo(OperatorVo vo) {
        OperatorInfo entity = new OperatorInfo();
        entity.setInsertTime(System.currentTimeMillis() / 1000);
        entity.setUpdateTime(System.currentTimeMillis() / 1000);
        entity.setWebUserId(vo.getWebUserId());
        entity.setGroupGenUuid(vo.getGroupGenUuid());
        entity.setOperator(vo.getOperator());
        entity.setGroupId(vo.getGroupId());
        entity.setUserIdList(vo.getUserIdList());
        entity.setStatus(OperatorStatusEnum.DEFAULT_STATUS.getType());
        if (!CollectionUtils.isEmpty(vo.getUserIdList())) {
            operatorInfoRepository.save(entity);
            return entity.getId();
        }
        return null;
    }
}
