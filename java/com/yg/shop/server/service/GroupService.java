package com.yg.shop.server.service;

import com.yg.base.code.ResultCode;
import com.yg.shop.server.pojo.ResultPage;
import com.yg.shop.server.pojo.dto.MyGroup;
import com.yg.shop.server.pojo.vo.AddMyGroup;
import com.yg.shop.server.pojo.vo.AddOrDelGroupTag;
import com.yg.shop.server.pojo.vo.MyGroupVo;

/**
 * 接口
 *
 * @author ljh
 * @date 2022/11/22 11:36
 */
public interface GroupService {
    /**
     * 查找我的群组
     *
     * @param vo 网页用户参数
     * @return 返回我的群组信息
     */
    ResultPage<MyGroup> findMyGroup(MyGroupVo vo);

    /**
     * 添加我的群组
     *
     * @param vo 参数
     * @return code
     */
    ResultCode addMyGroup(AddMyGroup vo);

    /**
     * 添加或删掉群组标签
     *
     * @param vo 参数
     * @return boolean
     */
    Boolean addOrDelGroupTag(AddOrDelGroupTag vo);
}
