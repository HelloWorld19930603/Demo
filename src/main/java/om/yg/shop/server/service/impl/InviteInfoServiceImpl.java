package om.yg.shop.server.service.impl;

import com.yg.shop.server.constant.InviteStatusEnum;
import com.yg.shop.server.constant.SystemConstant;
import com.yg.shop.server.entity.GroupMemberTag;
import com.yg.shop.server.entity.InviteInfo;
import com.yg.shop.server.pojo.ResultPage;
import com.yg.shop.server.pojo.dto.InviteInfoCountDto;
import com.yg.shop.server.pojo.dto.InviteInfoDto;
import com.yg.shop.server.pojo.vo.InviteInfoListVo;
import com.yg.shop.server.repository.GroupMemberTagRepository;
import com.yg.shop.server.repository.InviteInfoRepository;
import com.yg.shop.server.service.InviteInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 邀请接口的实现类
 *
 * @author ljh
 * @date 2022/11/22 11:36
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class InviteInfoServiceImpl implements InviteInfoService {

    private InviteInfoRepository inviteInfoRepository;
    private GroupMemberTagRepository groupMemberTagRepository;

    @Autowired
    public InviteInfoServiceImpl(InviteInfoRepository inviteInfoRepository
            , GroupMemberTagRepository groupMemberTagRepository) {
        this.inviteInfoRepository = inviteInfoRepository;
        this.groupMemberTagRepository = groupMemberTagRepository;
    }

    /**
     * 查找邀请信息
     *
     * @param vo 参数
     * @return 数据列表
     */
    @Override
    public ResultPage<InviteInfoDto> findInviteList(InviteInfoListVo vo) {
        ResultPage<InviteInfoDto> page = new ResultPage<>();
        List<InviteInfo> inviteInfoList = inviteInfoRepository.findInviteInfoList(vo.getGroupGenUuid());
        if (CollectionUtils.isEmpty(inviteInfoList)) {
            return page;
        }
        List<InviteInfoDto> collect = inviteInfoList.stream().map(i -> {
            InviteInfoDto inviteInfoDto = new InviteInfoDto();
            BeanUtils.copyProperties(i, inviteInfoDto);
            if (i.getInviteTime() == null) {
                i.setInviteTime(0L);
            }
            return inviteInfoDto;
        }).sorted(Comparator.comparing(InviteInfoDto::getInviteTime)
                .reversed()).collect(Collectors.toList());
        if (!StringUtils.isEmpty(vo.getStatus())) {
            collect = collect.stream().filter(i -> i.getStatus()
                    .equals(vo.getStatus())).sorted(Comparator.comparing(InviteInfoDto::getInviteTime)
                    .reversed()).collect(Collectors.toList());
        }
        if (!StringUtils.isEmpty(vo.getText())) {
            collect = collect.stream().filter(i ->
                    i.getNickname().contains(vo.getText())
            ).sorted(Comparator.comparing(InviteInfoDto::getInviteTime)
                    .reversed()).collect(Collectors.toList());
        }
        if (!StringUtils.isEmpty(vo.getInviteStartTime())) {
            collect = collect.stream().filter(i -> null != i.getInviteTime()).filter(i ->
                    i.getInviteTime() >= vo.getInviteStartTime()
                            && i.getInviteTime() <= vo.getInviteEndTime())
                    .sorted(Comparator.comparing(InviteInfoDto::getInviteTime)
                            .reversed())
                    .collect(Collectors.toList());
        }
        if (!StringUtils.isEmpty(vo.getRemindStartTime())) {
            collect = collect.stream().filter(i -> null != i.getSendInviteTime()).filter(i ->
                    i.getSendInviteTime() >= vo.getRemindStartTime()
                            && i.getSendInviteTime() <= vo.getRemindEndTime())
                    .sorted(Comparator.comparing(InviteInfoDto::getInviteTime)
                            .reversed())
                    .collect(Collectors.toList());
        }
        page.setCount((long) collect.size());
        if (!StringUtils.isEmpty(vo.getPageNum())) {
            collect = collect.stream().skip((vo.getPageNum() - 1) * vo.getPageSize())
                    .limit(vo.getPageSize()).peek(i -> {
                        GroupMemberTag groupMemberTag = groupMemberTagRepository
                                .findFirstByWebUserIdAndUserGenUuid(vo.getWebUserId(), i.getUserGenUuid());
                        if (!ObjectUtils.isEmpty(groupMemberTag)) {
                            i.setTagList(groupMemberTag.getTagList());
                        }
                        if (!StringUtils.isEmpty(i.getHeadPhoto())) {
                            i.setHeadPhoto(SystemConstant.replace + i.getHeadPhoto());
                        }
                    }).sorted(Comparator.comparing(InviteInfoDto::getInviteTime)
                            .reversed()).collect(Collectors.toList());
        }
        page.setList(collect);
        return page;
    }

    /**
     * 邀请数量统计
     *
     * @param uuid 群的唯一id
     * @return 统计信息
     */
    @Override
    public InviteInfoCountDto inviteCount(String uuid) {
        InviteInfoCountDto count = new InviteInfoCountDto();
        int inviteCount = 0;
        List<InviteInfo> inviteInfoList = inviteInfoRepository
                .findByGroupGenUuidAndStatus(uuid, InviteStatusEnum.RECEIVE.getType());
        inviteCount += inviteInfoList.size();
        List<InviteInfo> list = inviteInfoRepository
                .findByGroupGenUuidAndStatus(uuid, InviteStatusEnum.NOT_RECEIVE.getType());
        inviteCount += list.size();
        count.setNotInviteCount(list.size());
        count.setInviteCount(inviteCount);
        return count;
    }
}
