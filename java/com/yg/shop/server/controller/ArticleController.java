package com.yg.shop.server.controller;

import com.yg.base.code.ResultCode;
import com.yg.base.response.Result;
import com.yg.shop.server.pojo.ResultPage;
import com.yg.shop.server.pojo.dto.ArticleTree;
import com.yg.shop.server.pojo.vo.ArticleTreeVo;
import com.yg.shop.server.pojo.vo.LoginUserInfo;
import com.yg.shop.server.pojo.vo.SendArticleVo;
import com.yg.shop.server.service.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


/**
 * 文章
 *
 * @author ljh
 * @date 2022/11/29 19:46
 */
@RequestMapping("/article")
@RestController
@AllArgsConstructor
public class ArticleController extends BaseController {
    private final ArticleService articleService;

    /**
     * 搜索文章
     *
     * @param vo 参数
     * @returs 文章列表
     */
    @PostMapping("/searchArticle")
    public Result<ResultPage<ArticleTree>> searchArticle(@RequestBody @Valid ArticleTreeVo vo) {
        ResultPage<ArticleTree> page = articleService.articleTree(vo);
        return new Result<>(ResultCode.SUCCESS, page);
    }

    /**
     * 发布文章
     *
     * @param vo      前端参数
     * @param request 请求
     * @return boolean
     */
    @PostMapping("/sendArticle")
    public Result<Boolean> sendArticle(SendArticleVo vo, HttpServletRequest request) {
        LoginUserInfo user = getUser(request);
        Boolean b = articleService.sendArticle(vo, user);
        return new Result<>(ResultCode.SUCCESS, b);

    }

    /**
     * 查询文章详情
     *
     * @param articleGenUuid 文章的uuid
     * @param request        请求
     * @return 文章信息
     */
    @GetMapping("/info/{articleGenUuid}")
    public Result<ArticleTree> info(@PathVariable(name = "articleGenUuid") String articleGenUuid
            , HttpServletRequest request) {
        LoginUserInfo user = getUser(request);
        ArticleTree info = articleService.info(articleGenUuid, user);
        if (null == info) {
            return new Result<>(ResultCode.FAIL, "搜索无果,请联系工作人员解决", null);
        }
        return new Result<>(ResultCode.SUCCESS, info);
    }
}
