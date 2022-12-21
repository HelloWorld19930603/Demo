package com.yg.shop.server.controller;

import com.yg.base.code.ResultCode;
import com.yg.base.response.Result;
import com.yg.shop.server.pojo.ResultPage;
import com.yg.shop.server.pojo.dto.GroupMemberDto;
import com.yg.shop.server.pojo.dto.GroupMemberTagDto;
import com.yg.shop.server.pojo.vo.*;
import com.yg.shop.server.service.GroupMemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 * 群成员
 *
 * @author ljh
 * @date 2022/11/22 11:06
 */
@RestController
@RequestMapping("/groupMember")
public class GroupMemberController {

    private final GroupMemberService groupMemberService;

    public GroupMemberController(GroupMemberService groupMemberService) {
        this.groupMemberService = groupMemberService;
    }

    /**
     * 搜索群成员的信息,根据类型区角色,四个角色调用四次
     *
     * @param vo 参数
     * @return 数据列表
     */
    @PostMapping("/groupMember")
    public Result<ResultPage<GroupMemberDto>> findGroupMember(@RequestBody @Valid GroupMemberVo vo) {
        ResultPage<GroupMemberDto> page = groupMemberService.findGroupMember(vo);
        return new Result<>(ResultCode.SUCCESS, page);
    }

    /**
     * 搜索群成员总数(四次/groupMember/groupMember调完了之后,再掉这个方法)
     *
     * @param vo 参数
     * @return 总数
     */
    @PostMapping("/findGroupMemberCount")
    public Result<Long> findGroupMemberCount(@RequestBody @Valid FindGroupMemberCountVo vo) {
        Long l = groupMemberService.findGroupMemberCount(vo);
        return new Result<>(ResultCode.SUCCESS, l);
    }

    /**
     * 添加或删除群成员的标签
     *
     * @param vo 参数
     * @return boolean
     */
    @PostMapping("/addOrDelGroupMemberTag")
    public Result<Boolean> addOrDelGroupMemberTag(@RequestBody @Valid UpdateGroupMemberTagVo vo) {
        Boolean b = groupMemberService.addOrDelGroupMemberTag(vo);
        return new Result<>(ResultCode.SUCCESS, b);
    }

    /**
     * 搜索群成员标签
     *
     * @param vo 参数
     * @return 数据列表
     */
    @PostMapping("/findGroupMemberTag")
    public Result<List<GroupMemberTagDto>> findGroupMemberTag(@RequestBody @Valid UpdateGroupMemberTagVo vo) {
        System.out.println(vo);
        return new Result<>(ResultCode.SUCCESS, null);
    }

    /**
     * 批量导出群成员信息
     *
     * @param vo 参数
     */
    @PostMapping("/batchImportGroupMember")
    public void batchImportGroupMember(@RequestBody BatchImportGroupMemberVo vo, HttpServletResponse response) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String zipName = String.format(vo.getGroupNickname(), vo.getGroupNickname(), time);
        response.setContentType("application/zip");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + zipName + "\"");
        response.addHeader("Content-Transfer-Encoding", "binary");
        try (ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())) {
            groupMemberService.batchImportGroupMember(zos, vo);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
