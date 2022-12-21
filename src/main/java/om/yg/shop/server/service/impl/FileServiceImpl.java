package om.yg.shop.server.service.impl;

import cn.hutool.core.util.StrUtil;
import com.yg.shop.server.service.FileService;
import com.yg.shop.server.utils.OkHttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件service的实现类
 *
 * @author ljh
 * @date 2022/12/9 17:01
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Override
    public String upload(String url, String fileName, MultipartFile file) {
        fileName = "hbase" + StrUtil.SLASH + fileName;
        Map<String, String> params = new HashMap<>(16);
        params.put("file_time", String.valueOf(System.currentTimeMillis()));
        log.info("附件:" + fileName + "上传成功");
        try {
            OkHttpUtil.getInstance().postFileByte(url, params, fileName, file.getBytes(), "file");
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
