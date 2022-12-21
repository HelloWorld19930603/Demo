package om.yg.shop.server.controller;

import com.yg.base.code.ResultCode;
import com.yg.base.response.Result;
import com.yg.shop.server.pojo.ResultPage;
import com.yg.shop.server.pojo.dto.MyGroup;
import com.yg.shop.server.pojo.vo.AddMyGroup;
import com.yg.shop.server.pojo.vo.AddOrDelGroupTag;
import com.yg.shop.server.pojo.vo.MyGroupVo;
import com.yg.shop.server.service.GroupService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 群
 *
 * @author ljh
 * @date 2022/11/22 11:06
 */
@RestController
@RequestMapping("/group")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    /**
     * 添加我的群组
     *
     * @param vo 参数
     * @return boolean
     */
    @PostMapping("/addMyGroup")
    public Result<Boolean> addMyGroup(@RequestBody @Valid AddMyGroup vo) {
        ResultCode code = groupService.addMyGroup(vo);
        if (code.getCode() == 1) {
            return new Result<>(code, false);
        } else {
            return new Result<>(code, true);
        }
    }

    /**
     * 查询我的群组
     *
     * @param vo 参数
     * @return 群组信息
     */
    @PostMapping("/findMyGroup")
    public Result<ResultPage<MyGroup>> findMyGroup(@RequestBody @Valid MyGroupVo vo) {
        ResultPage<MyGroup> myGroup = groupService.findMyGroup(vo);
        return new Result<>(ResultCode.SUCCESS, myGroup);
    }

    /**
     * 添加或删除群组标签
     *
     * @param vo 参数
     * @return boolean
     */
    @PostMapping("/addOrDelGroupTag")
    public Result<Boolean> addOrDelGroupTag(@RequestBody @Valid AddOrDelGroupTag vo) {
        Boolean b = groupService.addOrDelGroupTag(vo);
        return new Result<>(ResultCode.SUCCESS, b);
    }

}
