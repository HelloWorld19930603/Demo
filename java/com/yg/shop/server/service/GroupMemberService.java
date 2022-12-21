package com.yg.shop.server.service;

import com.yg.shop.server.pojo.ResultPage;
import com.yg.shop.server.pojo.dto.GroupMemberDto;
import com.yg.shop.server.pojo.vo.BatchImportGroupMemberVo;
import com.yg.shop.server.pojo.vo.FindGroupMemberCountVo;
import com.yg.shop.server.pojo.vo.GroupMemberVo;
import com.yg.shop.server.pojo.vo.UpdateGroupMemberTagVo;

import java.io.IOException;
import java.util.zip.ZipOutputStream;

/**
 * 群成员接口
 *
 * @author ljh
 * @date 2022/11/22 11:36
 */
public interface GroupMemberService {

    /***
     * 根据类型搜索群成员
     * @param vo 参数
     * @return 分页信息
     */
    ResultPage<GroupMemberDto> findGroupMember(GroupMemberVo vo);

    /**
     * 获取群成员的个数
     *
     * @param vo 总数
     * @return
     */
    Long findGroupMemberCount(FindGroupMemberCountVo vo);

    /**
     * 添加或删除群成员标签
     *
     * @param vo 参数
     * @return boolean
     */
    Boolean addOrDelGroupMemberTag(UpdateGroupMemberTagVo vo);


    /**
     * 导出群成员信息
     *
     * @param zos 参数
     * @param vo  参数
     * @throws IOException
     */
    void batchImportGroupMember(ZipOutputStream zos, BatchImportGroupMemberVo vo) throws IOException;
}
