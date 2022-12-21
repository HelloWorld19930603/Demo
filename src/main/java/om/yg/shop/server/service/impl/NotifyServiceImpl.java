package om.yg.shop.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.yg.shop.server.constant.ArticleReadStatusEnum;
import com.yg.shop.server.entity.Notify;
import com.yg.shop.server.pojo.dto.NotifyDto;
import com.yg.shop.server.pojo.dto.NotifyResult;
import com.yg.shop.server.pojo.vo.NotifyVo;
import com.yg.shop.server.repository.NotifyRepository;
import com.yg.shop.server.service.NotifyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 通知接口的实现类
 *
 * @author ljh
 * @date 2022/11/22 11:36
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class NotifyServiceImpl implements NotifyService {

    private final NotifyRepository notifyRepository;

    @Override
    public NotifyResult searchNotify(NotifyVo vo) {
        NotifyResult page = new NotifyResult();
        List<Notify> list = notifyRepository.findByGroupGenUuid(vo.getGroupGenUuid());
        page.setCount((long) list.size());
        if (!CollectionUtils.isEmpty(list)) {
            int readNum = 0;
            for (Notify notify : list) {
                if (notify.getIsRead() == ArticleReadStatusEnum.READ.getType()) {
                    readNum++;
                }
            }
            page.setReadNum(readNum);
            page.setUnreadNum((list.size() - readNum));
            List<NotifyDto> collect = list.stream().map(i -> {
                NotifyDto dto = new NotifyDto();
                BeanUtil.copyProperties(i, dto);
                if (!StringUtils.isEmpty(dto.getHeadPhoto())) {
                    dto.setHeadPhoto(StrUtil.SLASH + dto.getHeadPhoto());
                }
                return dto;
            }).sorted(Comparator.comparing(NotifyDto::getOperatorTime).reversed())
                    .skip((vo.getPageNum() - 1) * vo.getPageSize())
                    .limit(vo.getPageSize())
                    .collect(Collectors.toList());
            page.setList(collect);
        }
        return page;
    }
}
