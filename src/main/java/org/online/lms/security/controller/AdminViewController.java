package org.online.lms.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.online.lms.lecture.dto.LectureInfoViewResponse;
import org.online.lms.lecture.service.LectureInfoService;
import org.online.lms.security.domain.Members;
import org.online.lms.security.service.AdminService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminViewController {
    private final AdminService adminService;
    private final LectureInfoService lectureInfoService;

    // 관리자 로그인 -> 마이페이지 이동
    @GetMapping("/admin/mypage")
    public String showAdminPage(){
        return "/page/security/adminPage";
    }

    // 수강생 관리 페이지로 이동
    @GetMapping("/admin/memberInfo")
    public String showMemberInfoByAdmin(@AuthenticationPrincipal Members member, Model model){
        List<Members> members = adminService.findAll();
        model.addAttribute("member", members);
        return "/page/security/memberInfoByAdmin";
    }

    // 관리자 페이지에서 개설 강의 목록 조회
    @GetMapping("/admin/memberInfo/lectures")
    public String findMembersByLectureNo(Model model) {
        List<LectureInfoViewResponse> lectureInfo = lectureInfoService.findAll().stream()
                .map(LectureInfoViewResponse::new)
                .toList();
        model.addAttribute("lectureInfo", lectureInfo); // 강의 목록 리스트 저장
        log.info("lectureInfo 정보 나와라 : " + lectureInfo);
        return "/page/security/adminLectureList";
    }

    // 관리자 페이지에서 개설 강의 목록 옆 '조회' 버튼 클릭 시, 수강생 목록 출력
    @GetMapping("/admin/memberInfo/lectureNo/{lectureNo}")
    public String findMemberInfoByLectureNo(@PathVariable Long lectureNo, Model model){

        List<Members> students = adminService.findStudentList(lectureNo);
        model.addAttribute("member", students);

        return "/page/security/studentList";
    }
}
