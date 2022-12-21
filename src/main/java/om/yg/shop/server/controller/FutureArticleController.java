package om.yg.shop.server.controller;

import com.yg.base.code.ResultCode;
import com.yg.base.response.Result;
import com.yg.shop.server.pojo.ResultPage;
import com.yg.shop.server.pojo.vo.*;
import com.yg.shop.server.service.FutureArticleService;
import com.yg.shop.server.pojo.dto.FutureArticleDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


/**
 * 定时贴,未来文章
 *
 * @author ljh
 * @date 2022/11/29 19:46
 */
@RequestMapping("/futureArticle")
@RestController
@AllArgsConstructor
public class FutureArticleController extends BaseController {

    private final FutureArticleService futureArticleService;

    /**
     * 创建定时贴
     *
     * @param vo 参数
     * @return boolean
     */
    @PostMapping("/createFutureArticle")
    public Result<Boolean> createFutureArticle(AddFutureArticleVo vo, HttpServletRequest request) {
        LoginUserInfo user = getUser(request);
        Boolean b = futureArticleService.createFutureArticle(vo, user);
        return new Result<>(ResultCode.SUCCESS, b);
    }

    /**
     * 搜索定时贴
     *
     * @param pageNum      当前页
     * @param pageSize     每页显示的数量
     * @param groupGenUuid 群唯一id
     * @return 数据
     */
    @GetMapping("/searchFutureArticle")
    public Result<ResultPage<FutureArticleDto>> searchFutureArticle(
            @RequestParam(defaultValue = "1", required = false) Integer pageNum,
            @RequestParam(defaultValue = "10", required = false) Integer pageSize,
            @RequestParam String groupGenUuid) {
        ResultPage<FutureArticleDto> list = futureArticleService.searchFutureArticle(groupGenUuid, pageNum, pageSize);
        return new Result<>(ResultCode.SUCCESS, list);
    }

    /**
     * 修改发帖
     *
     * @param vo 参数
     * @return boolean
     */
    @PostMapping("/updateFutureArticle")
    public Result<Boolean> updateFutureArticle(UpdateFutureArticleVo vo, HttpServletRequest request) {
        LoginUserInfo user = getUser(request);
        Boolean b = futureArticleService.updateFutureArticle(vo, user);
        return new Result<>(ResultCode.SUCCESS, b);
    }

    /**
     * 修改发帖时间和立即发布的接口
     *
     * @param vo      参数
     * @param request 请求
     * @return boolean
     */
    @PostMapping("/sendTime")
    public Result<Boolean> updateSendTime(@RequestBody @Valid SendTimeVo vo, HttpServletRequest request) {
        LoginUserInfo user = getUser(request);
        Boolean b = futureArticleService.updateSendTime(vo, user);
        return new Result<>(ResultCode.SUCCESS, b);
    }

    /**
     * 删除定时贴
     *
     * @param vo 参数
     * @return boolean
     */
    @DeleteMapping("/deleteFutureArticle")
    public Result<Boolean> delete(@RequestBody @Valid IdVo vo, HttpServletRequest request) {
        LoginUserInfo user = getUser(request);
        Boolean b = futureArticleService.del(vo, user);
        return new Result<>(ResultCode.SUCCESS, b);
    }


}
