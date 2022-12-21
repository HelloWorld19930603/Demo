package om.yg.shop.server.controller;

import com.yg.base.code.ResultCode;
import com.yg.base.response.Result;
import com.yg.shop.server.pojo.ResultPage;
import com.yg.shop.server.pojo.dto.InviteInfoCountDto;
import com.yg.shop.server.pojo.dto.InviteInfoDto;
import com.yg.shop.server.pojo.vo.InviteInfoListVo;
import com.yg.shop.server.service.InviteInfoService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 邀请管理
 *
 * @author ljh
 * @date 2022/11/22 11:06
 */
@RestController
@RequestMapping("/inviteInfo")
public class InviteInfoController {

    private final InviteInfoService inviteInfoService;

    public InviteInfoController(InviteInfoService inviteInfoService) {
        this.inviteInfoService = inviteInfoService;
    }

    /**
     * 获取邀请成员列表
     *
     * @param vo 参数
     * @return 数据列表
     */
    @PostMapping("/findInviteList")
    public Result<ResultPage<InviteInfoDto>> findInviteList(@RequestBody @Valid InviteInfoListVo vo) {
        ResultPage<InviteInfoDto> page = inviteInfoService.findInviteList(vo);
        return new Result<>(ResultCode.SUCCESS, page);
    }

    /**
     * 获取邀请的统计人数
     *
     * @param uuid 群的唯一标识,groupGenUuid
     * @return
     */
    @GetMapping("/inviteCount/{uuid}")
    public Result<InviteInfoCountDto> inviteCount(@PathVariable("uuid") String uuid) {
        InviteInfoCountDto count = inviteInfoService.inviteCount(uuid);
        return new Result<>(ResultCode.SUCCESS, count);
    }

}
