package om.yg.shop.server.service;


import com.yg.shop.server.pojo.ResultPage;
import com.yg.shop.server.pojo.dto.FutureArticleDto;
import com.yg.shop.server.pojo.vo.*;

/**
 * 定时贴,未来文章
 *
 * @author ljh
 * @date 2022/11/22 11:36
 */
public interface FutureArticleService {

    /**
     * 创建定时贴
     *
     * @param vo   前端参数
     * @param user 用户信息
     * @return boolean
     */
    Boolean createFutureArticle(AddFutureArticleVo vo, LoginUserInfo user);

    /**
     * 搜索发帖
     *
     * @param groupGenUuid 群id
     * @param pageNum      当前页
     * @param pageSize     每页显示的数量
     * @return 列表
     */
    ResultPage<FutureArticleDto> searchFutureArticle(String groupGenUuid, Integer pageNum, Integer pageSize);

    /**
     * 修改发帖信息
     *
     * @param vo   前端参数
     * @param user 用户信息
     * @return boolean
     */
    Boolean updateFutureArticle(UpdateFutureArticleVo vo, LoginUserInfo user);

    /**
     * 修改发帖时间
     *
     * @param vo   前端参数
     * @param user 用户信息
     * @return boolean
     */
    Boolean updateSendTime(SendTimeVo vo, LoginUserInfo user);

    /**
     * 删除定时贴
     *
     * @param vo   前端参数
     * @param user 用户信息
     * @return boolean
     */
    Boolean del(IdVo vo, LoginUserInfo user);
}
