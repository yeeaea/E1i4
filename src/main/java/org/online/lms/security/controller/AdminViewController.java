package org.online.lms.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminViewController {

    @GetMapping("/admin/mypage")
    public String showAdminPage(){
        return "/page/security/adminPage";
    }

}
