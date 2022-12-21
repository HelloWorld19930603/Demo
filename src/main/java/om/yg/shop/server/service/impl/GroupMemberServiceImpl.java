package om.yg.shop.server.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.yg.shop.server.constant.MemberTypeEnum;
import com.yg.shop.server.constant.OperatorLogEnum;
import com.yg.shop.server.constant.OperatorLogResultEnum;
import com.yg.shop.server.constant.SystemConstant;
import com.yg.shop.server.entity.GroupMember;
import com.yg.shop.server.entity.GroupMemberTag;
import com.yg.shop.server.entity.OperatorLog;
import com.yg.shop.server.pojo.ResultPage;
import com.yg.shop.server.pojo.dto.GroupMemberDto;
import com.yg.shop.server.pojo.vo.BatchImportGroupMemberVo;
import com.yg.shop.server.pojo.vo.FindGroupMemberCountVo;
import com.yg.shop.server.pojo.vo.GroupMemberVo;
import com.yg.shop.server.pojo.vo.UpdateGroupMemberTagVo;
import com.yg.shop.server.repository.GroupMemberRepository;
import com.yg.shop.server.repository.GroupMemberTagRepository;
import com.yg.shop.server.repository.OperatorLogRepository;
import com.yg.shop.server.service.GroupMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;

/**
 * 群成员接口实现类
 *
 * @author ljh
 * @date 2022/11/22 11:36
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class GroupMemberServiceImpl implements GroupMemberService {

    private GroupMemberRepository groupMemberRepository;
    private GroupMemberTagRepository groupMemberTagRepository;
    private OperatorLogRepository operatorLogRepository;
    private Integer count1 = 0;
    private Integer count2 = 0;

    @Autowired
    public GroupMemberServiceImpl(GroupMemberRepository groupMemberRepository
            , GroupMemberTagRepository groupMemberTagRepository
            , OperatorLogRepository operatorLogRepository) {
        this.groupMemberRepository = groupMemberRepository;
        this.groupMemberTagRepository = groupMemberTagRepository;
        this.operatorLogRepository = operatorLogRepository;
    }


    /**
     * 获取群成员信息
     *
     * @param vo 参数
     * @return 列表
     */
    @Override
    public ResultPage<GroupMemberDto> findGroupMember(GroupMemberVo vo) {
        ResultPage<GroupMemberDto> page = new ResultPage<>();
        List<GroupMember> memberList;

        // 搜索列表
        if (!StringUtils.isEmpty(vo.getUuid())) {
            memberList = groupMemberRepository.findGroupMemberList(vo.getUuid());
        } else if (StringUtils.isEmpty(vo.getText())) {
            memberList = groupMemberRepository.findByGroupGenUuidAndMemberType(vo.getGroupGenUuid(), vo.getMemberType());
        } else {
            memberList = groupMemberRepository.findByGroupGenUuidAndNicknameLike(vo.getGroupGenUuid(), vo.getText());
        }

        if (!StringUtils.isEmpty(vo.getMemberType())) {
            memberList = memberList.stream().filter(i -> i.getMemberType().equals(vo.getMemberType())).collect(Collectors.toList());
        }
        if (vo.getStartTime() != null) {
            memberList = memberList.stream().map(i -> {
                if (i.getJoinTime() == null) {
                    i.setJoinTime(0L);
                }
                if (i.getJoinTime() >= vo.getStartTime() && i.getJoinTime() <= vo.getEndTime()) {
                    return i;
                }
                return null;
            }).filter(Objects::nonNull).collect(Collectors.toList());
        }
        page.setCount((long) memberList.size());
        List<GroupMember> collect;
        if (vo.getPageNum() != null) {
            collect = memberList.stream().peek(i -> {
                if (i.getJoinTime() == null) {
                    i.setJoinTime(0L);
                }
            }).sorted(Comparator.comparing(GroupMember::getJoinTime)
                    .reversed()).skip((vo.getPageNum() - 1) * vo.getPageSize())
                    .limit(vo.getPageSize()).collect(Collectors.toList());
        } else {
            collect = memberList;
        }

        // 搜索标签
        List<GroupMemberDto> result = collect.stream().map(i -> {
            GroupMemberDto groupMemberDto = new GroupMemberDto();
            BeanUtils.copyProperties(i, groupMemberDto);
            GroupMemberTag groupMemberTagList = groupMemberTagRepository
                    .findFirstByWebUserIdAndUserGenUuid(vo.getWebUserId(), i.getUserGenUuid());
            if (ObjectUtils.isEmpty(groupMemberTagList)) {
                groupMemberDto.setTagList(Collections.emptySet());
            } else {
                groupMemberDto.setTagList(groupMemberTagList.getTagList());
            }
            if (i.getJoinTime() == null) {
                groupMemberDto.setJoinTime(0L);
            }
            if (!StringUtils.isEmpty(i.getHeadPhoto())) {
                groupMemberDto.setHeadPhoto(SystemConstant.replace + i.getHeadPhoto());
            }
            return groupMemberDto;
        }).sorted(Comparator.comparing(GroupMemberDto::getJoinTime)
                .reversed()).collect(Collectors.toList());
        page.setList(result);
        return page;
    }

    /**
     * 同级群成员的数量
     *
     * @param vo 总数
     * @return 群成员的数量
     */
    @Override
    public Long findGroupMemberCount(FindGroupMemberCountVo vo) {
        Integer type = 4;
        List<GroupMember> groupMemberList = groupMemberRepository
                .findByGroupGenUuidAndMemberType(vo.getGroupGenUuid(), type);
        if (CollectionUtils.isEmpty(groupMemberList)) {
            return 0L;
        } else {
            return (long) groupMemberList.size();
        }

    }

    /**
     * 添加群成员的标签信息
     *
     * @param vo 参数
     * @return boolean
     */
    @Override
    public Boolean addOrDelGroupMemberTag(UpdateGroupMemberTagVo vo) {
        try {
            GroupMemberTag groupMemberTags = groupMemberTagRepository
                    .findFirstByWebUserIdAndUserGenUuid(vo.getWebUserId(), vo.getUserGenUuid());
            if (ObjectUtils.isEmpty(groupMemberTags)) {
                groupMemberTags = saveGroupMemberTag(vo);
            } else {
                updateGroupMemberTag(vo, groupMemberTags);
            }
            groupMemberTagRepository.save(groupMemberTags);
            saveUpdateMemberTagLog(vo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 导出群成员的信息
     *
     * @param zos 参数
     * @param vo  参数
     * @throws IOException io异常
     */
    @Override
    public void batchImportGroupMember(ZipOutputStream zos, BatchImportGroupMemberVo vo) throws IOException {
        ArrayList<Map<String, String>> excelDataList = new ArrayList<>();
        List<GroupMemberDto> list;

        // 获取数据列表
        if (StringUtils.isEmpty(vo.getGroupGenUuid())) {
            list = vo.getList();
        } else if (!ObjectUtils.isEmpty(vo.getVo())) {
            GroupMemberVo groupMemberVo = vo.getVo();
            if (StringUtils.isEmpty(groupMemberVo.getMemberType())) {
                groupMemberVo.setMemberType(MemberTypeEnum.MEMBER.getType());
            }
            groupMemberVo.setWebUserId(vo.getWebUserId());
            groupMemberVo.setGroupGenUuid(vo.getGroupGenUuid());
            groupMemberVo.setPageSize(999999);
            ResultPage<GroupMemberDto> groupMember = findGroupMember(groupMemberVo);
            list = groupMember.getList();
        } else {
            GroupMemberVo groupMemberVo = new GroupMemberVo();
            groupMemberVo.setWebUserId(vo.getWebUserId());
            groupMemberVo.setUuid(vo.getGroupGenUuid());
            ResultPage<GroupMemberDto> groupMember = this.findGroupMember(groupMemberVo);
            list = groupMember.getList();
        }

        if (CollectionUtils.isEmpty(list)) {
            com.yg.application.shop.server.utils.ZipExpUtil.zipExcel(excelDataList, zos, MessageFormat.format(vo.getGroupNickname() + ".xlsx", "成员信息"));
            return;
        }
        Map<String, List<GroupMemberDto>> collect = list.stream()
                .collect(Collectors.groupingBy(GroupMemberDto::getUserGenUuid));

        list = list.stream().peek(i -> {
            List<GroupMemberDto> groupMemberDos = collect.get(i.getUserGenUuid());
            StringBuilder groupMemberType = new StringBuilder();
            for (GroupMemberDto groupMemberDto : groupMemberDos) {
                String text;
                switch (groupMemberDto.getMemberType()) {
                    case 1:
                        text = "管理员";
                        break;
                    case 2:
                        text = "版主";
                        break;
                    case 3:
                        text = "专家";
                        break;
                    default:
                        text = "普通成员";
                        break;
                }
                groupMemberType.append(text).append(" ");
            }
            i.setMemberTypeText(groupMemberType.toString());
        }).collect(Collectors.collectingAndThen
                (Collectors.toCollection(() -> new TreeSet<>
                        (Comparator.comparing(o -> o.getUserGenUuid() + ";" + o.getGroupGenUuid()))), ArrayList::new));

        for (GroupMemberDto dto : list) {
            String entryName;
            entryName = FileNameUtil.cleanInvalid(null);
            Map<String, String> row = new LinkedHashMap<>();
            row.put("账号昵称", dto.getNickname());
            row.put("群职位", dto.getMemberTypeText());
            String format = "";
            if (!StringUtils.isEmpty(dto.getJoinTime()) && dto.getJoinTime() != 0L) {
                Date date = new Date(dto.getJoinTime() * 1000);
                format = DateUtil.format(date, "yyyy-MM-dd HH:mm:ss");
            }
            row.put("入群时间", format);
            row.put("由谁邀请", dto.getJoinGroupType());
            row.put("简介", dto.getOtherInfo());
            StringBuilder sb = new StringBuilder();
            Set<String> tagList = dto.getTagList();
            for (String s : tagList) {
                sb.append(s).append(",");
            }
            if (!CollectionUtils.isEmpty(tagList)) {
                sb.replace(sb.length() - 1, sb.length(), "");
            }
            row.put("标签信息", sb.toString());
            row.put("url", SystemConstant.importMemberUrl + dto.getUserId());
            if (!StringUtils.isEmpty(dto.getHeadPhoto())) {
                dto.setHeadPhoto(dto.getHeadPhoto().replaceFirst(SystemConstant.replace, ""));
                downMemberHeadPhoto(zos, dto);
            }
            excelDataList.add(row);
            com.yg.application.shop.server.utils.ZipExpUtil.zip(new File(String.valueOf(dto)), zos, entryName, File::exists);
        }
        log.info("总头像个数{}，下载成功个数{}", count1, count2);
        com.yg.application.shop.server.utils.ZipExpUtil.zipExcel(excelDataList, zos, MessageFormat.format(vo.getGroupNickname() + ".xlsx", "成员信息"));
    }

    /**
     * 下载人员头像
     *
     * @param zos 参数
     * @param dto 参数
     */
    private void downMemberHeadPhoto(ZipOutputStream zos, GroupMemberDto dto) {
        String fileName = dto.getNickname() + ".jpg";
        String path = SystemConstant.hBaseServer + SystemConstant.hBaseUpLoadUrl + dto.getHeadPhoto();
        try {
            count1++;
            com.yg.application.shop.server.utils.ZipExpUtil.zipByte(zos, HttpUtil.downloadBytes(path), fileName);
            count2++;
        } catch (ZipException e) {
            log.error("重复的头像{}", path);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (HttpException e) {
            log.error("头像找不到{}", path);
        }
    }


    /**
     * 修改群成员标签
     *
     * @param vo              需要修改的参数
     * @param groupMemberTags 已经有的值
     */
    private void updateGroupMemberTag(UpdateGroupMemberTagVo vo, GroupMemberTag groupMemberTags) {
        Set<String> groupGenUuidList = groupMemberTags.getGroupGenUuidList();
        groupGenUuidList.add(vo.getGroupGenUuid());
        groupMemberTags.setGroupGenUuidList(groupGenUuidList);
        groupMemberTags.setTagList(vo.getTagList());
        groupMemberTags.setUpdateTime(System.currentTimeMillis() / 1000);
    }

    /**
     * 保存群成员标签
     *
     * @param vo 参数
     * @return 返回的实体类
     */
    @NotNull
    private GroupMemberTag saveGroupMemberTag(UpdateGroupMemberTagVo vo) {
        GroupMemberTag groupMemberTags;
        groupMemberTags = new GroupMemberTag();
        groupMemberTags.setWebUserId(vo.getWebUserId());
        groupMemberTags.setUserGenUuid(vo.getUserGenUuid());
        groupMemberTags.setTagList(vo.getTagList());
        groupMemberTags.setInsertTime(System.currentTimeMillis() / 1000);
        groupMemberTags.setUpdateTime(System.currentTimeMillis() / 1000);
        groupMemberTags.setGroupGenUuidList(Collections.singleton(vo.getGroupGenUuid()));
        return groupMemberTags;
    }

    /**
     * 保存添加群成员标签的日志信息
     *
     * @param vo 参数
     */
    private void saveUpdateMemberTagLog(UpdateGroupMemberTagVo vo) {
        OperatorLog entity = new OperatorLog();
        entity.setWebUserId(vo.getWebUserId());
        entity.setWebUsername(vo.getWebUserName());
        entity.setInsertTime(System.currentTimeMillis() / 1000);
        entity.setUpdateTime(System.currentTimeMillis() / 1000);
        entity.setResult(OperatorLogResultEnum.SUCCESS.getType());
        entity.setOperator(OperatorLogEnum.UPDATE_GROUP_MEMBER_TAG.getType());
        Map<String, String> map = new HashMap<>(16);
        map.put("tag_list", JSONArray.toJSONString(vo.getTagList()));
        map.put("group_gen_uuid", vo.getGroupGenUuid());
        map.put("user_gen_uuid", vo.getUserGenUuid());
        entity.setData(map);
        operatorLogRepository.save(entity);
    }
}
