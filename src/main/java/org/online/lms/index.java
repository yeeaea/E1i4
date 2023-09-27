package org.online.lms;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class index {
    @GetMapping("/") // 타임리프 레이아웃 테스트 화면
    public String home() {
        return "page/content";
    }
}
