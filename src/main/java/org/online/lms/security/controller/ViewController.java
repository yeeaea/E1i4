package org.online.lms.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")    // 메인 페이지로 이동
    public String login(){  // 커스텀 로그인 페이지 지정
        return "login";
    }


    @GetMapping("/lms/mypage")    // 마이페이지로 이동
    public String mypage(){
        return "/page/content";
    }

    @GetMapping("/lms/signup") // 회원가입 페이지로 이동
    public String signUp(){
        return "signUp";
    }

    @GetMapping("/lms/find-id") // 아이디 찾기 페이지로 이동
    public String findId(){
        return "findId";
    }

    @GetMapping("/lms/find-pw") // 비밀번호 찾기 페이지로 이동
    public String findPw(){
        return "findPw";
    }
}
