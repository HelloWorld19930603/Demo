package com.yg.shop.server.service;


import com.yg.shop.server.entity.AutoReplyTemplate;
import com.yg.shop.server.pojo.vo.IdVo;
import com.yg.shop.server.pojo.vo.LoginUserInfo;
import com.yg.shop.server.pojo.vo.ReplyTemplateVo;
import com.yg.shop.server.pojo.vo.SetDefaultVo;

import java.util.List;

/**
 * 自动评论
 *
 * @author ljh
 * @date 2022/11/22 11:36
 */
public interface AutoReplyTemplateService {

    /**
     * 添加自动评论
     *
     * @param loginUserInfo 参数
     * @param vo            参数
     * @return boolean
     */
    Boolean addReplyTemplate(ReplyTemplateVo vo, LoginUserInfo loginUserInfo);


    /**
     * 搜索群的自动评论
     *
     * @param groupGenUuid 群id
     * @return 列表
     */
    List<AutoReplyTemplate> searchTemplate(String groupGenUuid);

    /**
     * 编辑自动评论
     *
     * @param vo   前台参数
     * @param user 用户参数
     * @return boolean
     */
    Boolean updateReplyTemplate(ReplyTemplateVo vo, LoginUserInfo user);

    /**
     * 删除自动评论
     *
     * @param vo   前端参数
     * @param user 用户参数
     * @return boolean
     */
    Boolean del(IdVo vo, LoginUserInfo user);


    /**
     * 设为默认
     *
     * @param vo   前端参数
     * @param user 用户参数
     * @return boolean
     */
    Boolean setDefault(SetDefaultVo vo, LoginUserInfo user);
}
