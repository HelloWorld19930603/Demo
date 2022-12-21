package com.yg.shop.server.controller;

import com.yg.base.code.ResultCode;
import com.yg.base.response.Result;
import com.yg.shop.server.pojo.ResultPage;
import com.yg.shop.server.pojo.dto.ArticleFollowDto;
import com.yg.shop.server.service.ArticleFollowService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 文章点赞
 *
 * @author ljh
 * @date 2022/11/29 19:46
 */
@RequestMapping("/articleFollow")
@RestController
@AllArgsConstructor
public class ArticleFollowController extends BaseController {
    private final ArticleFollowService articleFollowService;

    /**
     * 获取文章的点赞信息
     *
     * @param pageNum        当前页
     * @param pageSize       每页显示的数量
     * @param articleGenUuid 文章生成的唯一标识  如:aaa
     * @return 点赞信息
     */
    @GetMapping("/getArticleFollow")
    private Result<ResultPage<ArticleFollowDto>> getArticleFollow(
            @RequestParam(defaultValue = "1", required = false) Integer pageNum
            , @RequestParam(defaultValue = "10", required = false) Integer pageSize
            , @RequestParam String articleGenUuid) {
        ResultPage<ArticleFollowDto> page = articleFollowService.getArticleFollow(articleGenUuid, pageNum, pageSize);
        return new Result<>(ResultCode.SUCCESS, page);
    }
}
