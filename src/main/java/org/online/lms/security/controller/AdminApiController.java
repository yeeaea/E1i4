package org.online.lms.security.controller;

import lombok.RequiredArgsConstructor;
import org.online.lms.security.domain.Members;
import org.online.lms.security.service.AdminService;
import org.online.lms.security.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminApiController {
    private final AdminService adminService;

    // 관리자 페이지에서 해당 강의에 대한 수강생 목록 조회
//    @GetMapping("/memberInfo/lectures"){
//        public String findStudentList(@RequestParam Long lectureNo){
//            List<Members> students = adminService.findStudentList(lectureNo);
//
//        }
//    }
}
