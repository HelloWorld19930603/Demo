package om.yg.shop.server.service;


import com.yg.shop.server.pojo.vo.OperatorVo;

/**
 * 操作接口
 *
 * @author ljh
 * @date 2022/11/22 11:36
 */
public interface OperationService {

    /**
     * 保存操作
     *
     * @param vo 参数
     * @return 返回值
     */
    Boolean saveOperator(OperatorVo vo);
}
