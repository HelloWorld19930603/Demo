package om.yg.shop.server.controller;

import com.yg.shop.server.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 *
 * @author ljh
 * @date 2022/12/9 17:18
 */
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final FileService fileService;

    @PostMapping("/test")
    public String test() {
        int i = 1 / 0;
        return "dsaf";
    }
}
