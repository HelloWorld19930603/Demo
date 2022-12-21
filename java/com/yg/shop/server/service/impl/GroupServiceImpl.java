package com.yg.shop.server.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.yg.base.code.ResultCode;
import com.yg.shop.server.constant.*;
import com.yg.shop.server.entity.*;
import com.yg.shop.server.pojo.ResultPage;
import com.yg.shop.server.pojo.vo.AddOrDelGroupTag;
import com.yg.shop.server.repository.*;
import com.yg.shop.server.pojo.dto.MyGroup;
import com.yg.shop.server.pojo.vo.AddMyGroup;
import com.yg.shop.server.pojo.vo.MyGroupVo;
import com.yg.shop.server.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 实现类
 *
 * @author ljh
 * @date 2022/11/22 11:36
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private GroupRepository groupRepository;
    private GroupTagRepository groupTagRepository;
    private OperatorLogRepository operatorLogRepository;
    private GroupMemberRepository groupMemberRepository;
    private InviteInfoRepository inviteInfoRepository;
    private JoinGroupRequestRepository joinGroupRequestRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository
            , GroupTagRepository groupTagRepository
            , OperatorLogRepository operatorLogRepository
            , GroupMemberRepository groupMemberRepository
            , InviteInfoRepository inviteInfoRepository
            , JoinGroupRequestRepository joinGroupRequestRepository) {
        this.groupRepository = groupRepository;
        this.groupTagRepository = groupTagRepository;
        this.operatorLogRepository = operatorLogRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.inviteInfoRepository = inviteInfoRepository;
        this.joinGroupRequestRepository = joinGroupRequestRepository;
    }


    /**
     * 群列表
     *
     * @param vo 网页用户参数
     * @return 列表
     */
    @Override
    public ResultPage<MyGroup> findMyGroup(MyGroupVo vo) {
        // 成员类型
        Integer type = 4;

        ResultPage<MyGroup> result = new ResultPage<>();
        List<Group> webUserIdList;
        if (!StringUtils.isEmpty(vo.getText())) {
            webUserIdList = groupRepository.findByWebUserIdListAndNicknameLike(vo.getWebUserId(), vo.getText());
        } else {
            webUserIdList = groupRepository.findByWebUserIdList(vo.getWebUserId());
        }
        result.setCount((long) webUserIdList.size());
        if (CollectionUtils.isEmpty(webUserIdList)) {
            return result;
        }
        List<MyGroup> collect;
        collect = webUserIdList.stream().map(i -> {
            MyGroup myGroup = new MyGroup();
            BeanUtils.copyProperties(i, myGroup);
            return myGroup;
        }).collect(Collectors.toList());
        collect = collect.stream().skip((vo.getPageNum() - 1) * vo.getPageSize())
                .limit(vo.getPageSize()).collect(Collectors.toList());
        // uuid不为null,查询标签
        collect = collect.stream().peek(i -> {
            if (!StringUtils.isEmpty(i.getGroupGenUuid())) {
                GroupTag groupTag = groupTagRepository.findFirstByWebUserIdAndGroupGenUuid
                        (vo.getWebUserId(), i.getGroupGenUuid());
                if (!ObjectUtils.isEmpty(groupTag)) {
                    i.setTagList(groupTag.getTagList());
                } else {
                    i.setTagList(Collections.emptyList());
                }
                List<GroupMember> groupMemberList = groupMemberRepository
                        .findByGroupGenUuidAndMemberType(i.getGroupGenUuid(), type);
                i.setMemberNumber((long) groupMemberList.size());
                // 入群未处理
                List<JoinGroupRequest> joinGroup = joinGroupRequestRepository.findByGroupGenUuidAndStatus
                        (i.getGroupGenUuid(), JoinGroupRequestStatusEnum.DEFAULT_STATUS.getType());
                i.setJoinGroupNumber((long) joinGroup.size());
                i.setJoinGroupNoHandleNumber((long) joinGroup.size());
                // 处理
                long handle;
                // 入群拒绝
                long refuse = 0L;
                joinGroup = joinGroupRequestRepository.findByGroupGenUuidAndStatus
                        (i.getGroupGenUuid(), JoinGroupRequestStatusEnum.REFUSE.getType());
                refuse += joinGroup.size();
                joinGroup = joinGroupRequestRepository.findByGroupGenUuidAndStatus
                        (i.getGroupGenUuid(), JoinGroupRequestStatusEnum.REFUSE_PROCESSING.getType());
                refuse += joinGroup.size();
                i.setJoinGroupRefuseNumber(refuse);
                // 同意
                long receive = 0L;
                joinGroup = joinGroupRequestRepository.findByGroupGenUuidAndStatus
                        (i.getGroupGenUuid(), JoinGroupRequestStatusEnum.RECEIVE.getType());
                receive += joinGroup.size();
                joinGroup = joinGroupRequestRepository.findByGroupGenUuidAndStatus
                        (i.getGroupGenUuid(), JoinGroupRequestStatusEnum.RECEIVE_PROCESSING.getType());
                receive += joinGroup.size();
                i.setJoinGroupAgreeNumber(receive);
                handle = refuse + receive;
                i.setJoinGroupHandleNumber(handle);
                // 邀请数量
                long inviteCount = 0L;
                // 未处理数量
                long noInviteNumber = 0L;
                List<InviteInfo> inviteInfos = inviteInfoRepository
                        .findByGroupGenUuidAndStatus(i.getGroupGenUuid(), InviteStatusEnum.NOT_RECEIVE.getType());
                noInviteNumber += inviteInfos.size();
                inviteCount += inviteInfos.size();
                inviteInfos = inviteInfoRepository.findByGroupGenUuidAndStatus
                        (i.getGroupGenUuid(), InviteStatusEnum.RECEIVE.getType());
                inviteCount += inviteInfos.size();
                i.setInviteNumber(inviteCount);
                i.setNoInviteNumber(noInviteNumber);
                if (!StringUtils.isEmpty(i.getHeadPhoto())) {
                    i.setHeadPhoto(SystemConstant.replace + i.getHeadPhoto());
                }
            } else {
                i.setTagList(i.getTagList());
            }

        }).collect(Collectors.toList());
        result.setList(collect);
        return result;
    }

    /**
     * 添加群组
     *
     * @param vo 参数
     * @return resultCode
     */
    @Override
    public ResultCode addMyGroup(AddMyGroup vo) {
        List<Group> list = groupRepository.findGroupFromUrl(vo.getUrl());
        addGroupLog(vo);
        if (!CollectionUtils.isEmpty(list)) {
            Group group = list.get(0);
            List<String> webUserIdList = group.getWebUserIdList();
            webUserIdList.add(vo.getWebUserId());
            group.setWebUserIdList(webUserIdList);
            group.setUpdateTime(System.currentTimeMillis() / 1000);
            if (!CollectionUtils.isEmpty(vo.getTagList())) {
                List<String> tagList = group.getTagList();
                tagList.addAll(vo.getTagList());
                group.setTagList(tagList);
            }
            groupRepository.save(group);
            return ResultCode.SUCCESS;
        } else {
            Group group = new Group();
            group.setUrl(vo.getUrl());
            group.setBotStatus(BotStatusEnum.NO_BOT.getType());
            group.setWebUserIdList(Collections.singletonList(vo.getWebUserId()));
            group.setUpdateTime(System.currentTimeMillis() / 1000);
            group.setInsertTime(System.currentTimeMillis() / 1000);
            if (!CollectionUtils.isEmpty(vo.getTagList())) {
                group.setTagList(vo.getTagList());
            } else {
                group.setTagList(Collections.emptyList());
            }
            groupRepository.save(group);
            return ResultCode.SUCCESS;
        }
    }

    /**
     * 添加或修改群标签
     *
     * @param vo 参数
     * @return boolean
     */
    @Override
    public Boolean addOrDelGroupTag(AddOrDelGroupTag vo) {
        try {
            saveUpdateGroupTagLog(vo);
            if (!StringUtils.isEmpty(vo.getGroupUrl())) {
                saveGroupTagNotUuid(vo);
            } else {
                GroupTag groupTag = groupTagRepository.findFirstByWebUserIdAndGroupGenUuid
                        (vo.getWebUserId(), vo.getGroupGenUuid());
                if (ObjectUtils.isEmpty(groupTag)) {
                    saveGroupTag(vo);
                } else {
                    groupTag.setUpdateTime(System.currentTimeMillis() / 1000);
                    groupTag.setTagList(vo.getTagList());
                    groupTagRepository.save(groupTag);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 没有uuid的情况下保存标签
     *
     * @param vo 参数
     */
    private void saveGroupTagNotUuid(AddOrDelGroupTag vo) {
        List<Group> groupFromUrl = groupRepository.findGroupFromUrl(vo.getGroupUrl());
        if (!CollectionUtils.isEmpty(groupFromUrl)) {
            Group group = groupFromUrl.get(0);
            group.setTagList(vo.getTagList());
            groupRepository.save(group);
        }
    }

    /**
     * 保存修改群标签的日志
     *
     * @param vo 参数
     */
    private void saveUpdateGroupTagLog(AddOrDelGroupTag vo) {
        OperatorLog entity = new OperatorLog();
        entity.setInsertTime(System.currentTimeMillis() / 1000);
        entity.setUpdateTime(System.currentTimeMillis() / 1000);
        entity.setWebUserId(vo.getWebUserId());
        entity.setWebUsername(vo.getWebUserName());
        entity.setOperator(OperatorLogEnum.UPDATE_GROUP_TAG.getType());
        Map<String, String> map = new HashMap<>(16);
        if (StringUtils.isEmpty(vo.getGroupGenUuid())) {
            map.put("group_url", vo.getGroupUrl());
        } else {
            map.put("group_gen_uuid", vo.getGroupGenUuid());
        }
        map.put("tag_list", JSONArray.toJSONString(vo.getTagList()));
        entity.setData(map);
        entity.setResult(OperatorLogResultEnum.SUCCESS.getType());
        operatorLogRepository.save(entity);
    }

    /**
     * 保存群标签
     *
     * @param vo 参数
     */
    private void saveGroupTag(AddOrDelGroupTag vo) {
        GroupTag entity = new GroupTag();
        entity.setInsertTime(System.currentTimeMillis() / 1000);
        entity.setUpdateTime(System.currentTimeMillis() / 1000);
        entity.setWebUserId(vo.getWebUserId());
        entity.setGroupGenUuid(vo.getGroupGenUuid());
        entity.setTagList(vo.getTagList());
        groupTagRepository.save(entity);
    }


    /**
     * 添加群操作日志
     *
     * @param vo 参数
     */
    private void addGroupLog(AddMyGroup vo) {
        OperatorLog entity = new OperatorLog();
        entity.setOperator(OperatorLogEnum.ADD_GROUP.getType());
        entity.setResult(OperatorLogResultEnum.NOT_START.getType());
        entity.setWebUserId(vo.getWebUserId());
        entity.setWebUsername(vo.getWebUserName());
        HashMap<String, String> map = new HashMap<>(16);
        map.put("content", OperatorLogEnum.ADD_GROUP.getName());
        entity.setData(map);
        entity.setInsertTime(System.currentTimeMillis() / 1000);
        entity.setUpdateTime(System.currentTimeMillis() / 1000);
        operatorLogRepository.save(entity);
    }


}
