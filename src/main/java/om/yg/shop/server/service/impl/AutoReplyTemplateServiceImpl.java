package om.yg.shop.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.yg.shop.server.constant.OperatorLogEnum;
import com.yg.shop.server.constant.OperatorLogResultEnum;
import com.yg.shop.server.constant.SystemConstant;
import com.yg.shop.server.constant.TemplateDefaultEnum;
import com.yg.shop.server.entity.AutoReplyTemplate;
import com.yg.shop.server.entity.OperatorLog;
import com.yg.shop.server.pojo.vo.IdVo;
import com.yg.shop.server.pojo.vo.LoginUserInfo;
import com.yg.shop.server.pojo.vo.ReplyTemplateVo;
import com.yg.shop.server.pojo.vo.SetDefaultVo;
import com.yg.shop.server.repository.AutoReplyTemplateRepository;
import com.yg.shop.server.repository.OperatorLogRepository;
import com.yg.shop.server.service.AutoReplyTemplateService;
import com.yg.shop.server.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 自动评论接口的实现类
 *
 * @author ljh
 * @date 2022/11/22 11:36
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AutoReplyTemplateServiceImpl implements AutoReplyTemplateService {

    private final AutoReplyTemplateRepository autoReplyTemplateRepository;
    private final OperatorLogRepository operatorLogRepository;
    private final FileService fileService;

    @Override
    public Boolean addReplyTemplate(ReplyTemplateVo vo, LoginUserInfo loginUserInfo) {

        try {
            List<AutoReplyTemplate> list = autoReplyTemplateRepository.findByGroupGenUuid(vo.getGroupGenUuid());
            if (!CollectionUtils.isEmpty(list)) {
                if (list.size() >= vo.getReplyNum()) {
                    return false;
                }
            }

            // 添加自动评论
            AutoReplyTemplate entity = BeanUtil.toBean(vo, AutoReplyTemplate.class);
            if (!ObjectUtils.isEmpty(vo.getFile())) {
                String fileName = SystemConstant.shop + StrUtil.SLASH + vo.getGroupGenUuid() + StrUtil.SLASH + vo.getFile().getOriginalFilename();
                String upload = fileService.upload(SystemConstant.uploadUrl, fileName, vo.getFile());
                entity.setMediaFile(upload);
            }
            entity.setInsertTime(System.currentTimeMillis() / 1000);
            entity.setUpdateTime(System.currentTimeMillis() / 1000);


            autoReplyTemplateRepository.save(entity);

            // 保存日志
            OperatorLog operatorLog = new OperatorLog();
            BeanUtil.copyProperties(loginUserInfo, operatorLog);
            operatorLog.setOperator(OperatorLogEnum.ADD_AUTO_COMMENT.getType());
            Map<String, String> map = new HashMap<>(16);
            map.put("group_gen_uuid", vo.getGroupGenUuid());
            operatorLog.setData(map);
            operatorLog.setResult(OperatorLogResultEnum.SUCCESS.getType());
            operatorLog.setInsertTime(System.currentTimeMillis() / 1000);
            operatorLog.setUpdateTime(System.currentTimeMillis() / 1000);
            operatorLogRepository.save(operatorLog);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<AutoReplyTemplate> searchTemplate(String groupGenUuid) {
        List<AutoReplyTemplate> list = autoReplyTemplateRepository.findByGroupGenUuid(groupGenUuid);
        if (CollectionUtils.isEmpty(list)) {
            return ListUtil.empty();
        } else {
            list = list.stream().peek(i -> {
                if (!StringUtils.isEmpty(i.getMediaFile())) {
                    i.setMediaFile(StrUtil.SLASH + i.getMediaFile());
                }
            }).collect(Collectors.toList());
            list.sort(Comparator.comparing(AutoReplyTemplate::getUpdateTime));
            return list;
        }
    }

    @Override
    public Boolean updateReplyTemplate(ReplyTemplateVo vo, LoginUserInfo user) {
        try {
            Map<String, String> map = new HashMap<>(16);
            map.put("group_gen_uuid", vo.getGroupGenUuid());
            Optional<AutoReplyTemplate> byId = autoReplyTemplateRepository.findById(vo.getId());
            map.put("update_before", JSONArray.toJSONString(byId));
            AutoReplyTemplate autoReplyTemplate = byId.get();
            BeanUtil.copyProperties(vo, autoReplyTemplate);
            autoReplyTemplate.setUpdateTime(System.currentTimeMillis() / 1000);
            if (null != vo.getFile()) {
                // 保存媒体文件
                String fileName = SystemConstant.shop + StrUtil.SLASH + vo.getGroupGenUuid() + StrUtil.SLASH + vo.getFile().getOriginalFilename();
                String upload = fileService.upload(SystemConstant.uploadUrl, fileName, vo.getFile());
                autoReplyTemplate.setMediaFile(upload);
            }
            if (!StringUtils.isEmpty(vo.getDelImgPath())) {
                autoReplyTemplate.setMediaFile(StrUtil.SPACE);
            }
            AutoReplyTemplate save = autoReplyTemplateRepository.save(autoReplyTemplate);
            map.put("update_after", JSONArray.toJSONString(save));
            OperatorLog log = new OperatorLog();
            log.setInsertTime(System.currentTimeMillis() / 1000);
            log.setUpdateTime(System.currentTimeMillis() / 1000);
            log.setWebUserId(user.getWebUserId());
            log.setWebUsername(user.getWebUsername());
            log.setOperator(OperatorLogEnum.UPDATE_AUTO_COMMENT.getType());
            log.setResult(OperatorLogResultEnum.SUCCESS.getType());
            log.setData(map);
            operatorLogRepository.save(log);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean del(IdVo vo, LoginUserInfo user) {
        try {
            Map<String, String> map = new HashMap<>(16);
            AutoReplyTemplate autoReplyTemplate = autoReplyTemplateRepository.findById(vo.getId()).get();
            map.put("info", JSONArray.toJSONString(autoReplyTemplate));
            map.put("group_gen_uuid", vo.getGroupGenUuid());
            OperatorLog log = new OperatorLog();
            log.setInsertTime(System.currentTimeMillis() / 1000);
            log.setUpdateTime(System.currentTimeMillis() / 1000);
            log.setWebUserId(user.getWebUserId());
            log.setWebUsername(user.getWebUsername());
            log.setData(map);
            log.setResult(OperatorLogResultEnum.SUCCESS.getType());
            log.setOperator(OperatorLogEnum.DEL_AUTO_COMMENT.getType());
            operatorLogRepository.save(log);
            autoReplyTemplateRepository.deleteById(vo.getId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean setDefault(SetDefaultVo vo, LoginUserInfo user) {
        try {
            if (!StringUtils.isEmpty(vo.getOldId())) {
                AutoReplyTemplate oldReplyTemplate = autoReplyTemplateRepository.findById(vo.getOldId()).get();
                oldReplyTemplate.setIsDefault(TemplateDefaultEnum.NO.getType());
                autoReplyTemplateRepository.save(oldReplyTemplate);
            }
            AutoReplyTemplate newReplyTemplate = autoReplyTemplateRepository.findById(vo.getNewId()).get();
            newReplyTemplate.setIsDefault(TemplateDefaultEnum.YES.getType());
            autoReplyTemplateRepository.save(newReplyTemplate);
            // 保存日志
            OperatorLog operatorLog = new OperatorLog();
            operatorLog.setOperator(OperatorLogEnum.SET_TEMPLATE_EFAULT.getType());
            Map<String, String> map = new HashMap<>(16);
            map.put("group_gen_uuid", vo.getGroupGenUuid());
            operatorLog.setData(map);
            operatorLog.setResult(OperatorLogResultEnum.SUCCESS.getType());
            operatorLog.setInsertTime(System.currentTimeMillis() / 1000);
            operatorLog.setUpdateTime(System.currentTimeMillis() / 1000);
            operatorLogRepository.save(operatorLog);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
