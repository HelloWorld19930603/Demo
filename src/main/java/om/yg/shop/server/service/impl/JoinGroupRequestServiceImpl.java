package om.yg.shop.server.service.impl;

import com.yg.shop.server.constant.JoinGroupRequestStatusEnum;
import com.yg.shop.server.constant.SystemConstant;
import com.yg.shop.server.entity.GroupMemberTag;
import com.yg.shop.server.entity.JoinGroupRequest;
import com.yg.shop.server.pojo.dto.JoinGroupRequestDto;
import com.yg.shop.server.pojo.dto.JoinGroupRequestResultDto;
import com.yg.shop.server.pojo.vo.JoinGroupRequestListVo;
import com.yg.shop.server.repository.GroupMemberTagRepository;
import com.yg.shop.server.repository.JoinGroupRequestRepository;
import com.yg.shop.server.service.JoinGroupRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 入群申请实现类
 *
 * @author ljh
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class JoinGroupRequestServiceImpl implements JoinGroupRequestService {

    private JoinGroupRequestRepository joinGroupRequestRepository;
    private GroupMemberTagRepository groupMemberTagRepository;

    @Autowired
    public JoinGroupRequestServiceImpl(JoinGroupRequestRepository joinGroupRequestRepository
            , GroupMemberTagRepository groupMemberTagRepository) {
        this.joinGroupRequestRepository = joinGroupRequestRepository;
        this.groupMemberTagRepository = groupMemberTagRepository;
    }

    /**
     * 查询入群邀请信息
     *
     * @param vo 参数
     * @return 列表
     */
    @Override
    public JoinGroupRequestResultDto findJoinGroupRequestList(JoinGroupRequestListVo vo) {
        JoinGroupRequestResultDto page = new JoinGroupRequestResultDto();
        joinGroupCount(vo.getGroupGenUuid(), page);
        List<JoinGroupRequest> joinGroup;
        if (StringUtils.isEmpty(vo.getStatus())) {
            joinGroup = joinGroupRequestRepository.findJoinGroup(vo.getGroupGenUuid());
        } else {
            joinGroup = joinGroupRequestRepository.findByGroupGenUuidAndStatus(vo.getGroupGenUuid(), vo.getStatus());
            if (vo.getStatus().equals(JoinGroupRequestStatusEnum.RECEIVE_PROCESSING.getType())) {
                List<JoinGroupRequest> list = joinGroupRequestRepository.findByGroupGenUuidAndStatus
                        (vo.getGroupGenUuid(), JoinGroupRequestStatusEnum.REFUSE_PROCESSING.getType());
                joinGroup.addAll(list);
            }
        }
        if (CollectionUtils.isEmpty(joinGroup)) {
            return page;
        }
        if (!StringUtils.isEmpty(vo.getStartTime())) {
            joinGroup = joinGroup.stream().filter(i -> i.getRequestTime() >= vo.getStartTime()
                    & i.getRequestTime() <= vo.getEndTime()).collect(Collectors.toList());
        }
        if (!StringUtils.isEmpty(vo.getText())) {
            joinGroup = joinGroup.stream().filter(i -> i.getNickname()
                    .contains(vo.getText())).collect(Collectors.toList());
        }
        if (CollectionUtils.isEmpty(joinGroup)) {
            return page;
        }
        List<JoinGroupRequestDto> collect = joinGroup.stream().map(i -> {
            JoinGroupRequestDto dto = new JoinGroupRequestDto();
            BeanUtils.copyProperties(i, dto);
            dto.setTagList(Collections.emptySet());
            if (i.getJoinTime() == null) {
                dto.setJoinTime(0L);
            }
            return dto;
        }).sorted(Comparator.comparing(JoinGroupRequestDto::getJoinTime)
                .reversed()).collect(Collectors.toList());
        page.setCount(collect.size());
        if (!StringUtils.isEmpty(vo.getPageNum())) {
            collect = collect.stream().skip((vo.getPageNum() - 1) * vo.getPageSize())
                    .limit(vo.getPageSize()).peek(i -> {
                        GroupMemberTag tag = groupMemberTagRepository
                                .findFirstByWebUserIdAndUserGenUuid(vo.getWebUserId(), i.getUserGenUuid());
                        if (ObjectUtils.isEmpty(tag)) {
                            i.setTagList(Collections.emptySet());
                        } else {
                            i.setTagList(tag.getTagList());
                        }
                        if (!StringUtils.isEmpty(i.getHeadPhoto())) {
                            i.setHeadPhoto(SystemConstant.replace + i.getHeadPhoto());
                        }
                    }).sorted(Comparator.comparing(JoinGroupRequestDto::getJoinTime)
                            .reversed()).collect(Collectors.toList());
        }
        page.setList(collect);
        return page;
    }

    /**
     * 入群申请的数量
     *
     * @param groupGenUuid 群唯一id
     * @param page         返回结果
     */
    private void joinGroupCount(String groupGenUuid, JoinGroupRequestResultDto page) {
        // 申请
        int applyCount = 0;
        // 处理
        int handleCount = 0;
        List<JoinGroupRequest> list = joinGroupRequestRepository.findByGroupGenUuidAndStatus(groupGenUuid, 0);
        applyCount += list.size();
        list = joinGroupRequestRepository.findByGroupGenUuidAndStatus(groupGenUuid, 1);
        handleCount += list.size();
        applyCount += list.size();
        list = joinGroupRequestRepository.findByGroupGenUuidAndStatus(groupGenUuid, 2);
        handleCount += list.size();
        applyCount += list.size();
        list = joinGroupRequestRepository.findByGroupGenUuidAndStatus(groupGenUuid, 3);
        handleCount += list.size();
        applyCount += list.size();
        list = joinGroupRequestRepository.findByGroupGenUuidAndStatus(groupGenUuid, 4);
        handleCount += list.size();
        applyCount += list.size();
        page.setApplyCount(applyCount);
        page.setHandleCount(handleCount);
        // 未处理
        int noHandleCount = applyCount - handleCount;
        page.setNoHandleCount(noHandleCount);
    }
}
