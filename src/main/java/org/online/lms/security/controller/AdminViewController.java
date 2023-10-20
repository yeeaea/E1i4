package org.online.lms.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.online.lms.lecture.domain.LectureInfo;
import org.online.lms.lecture.dto.LectureInfoViewResponse;
import org.online.lms.lecture.service.LectureInfoService;
import org.online.lms.security.domain.Members;
import org.online.lms.security.service.AdminService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Pageable;
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


    // 관리자 로그인 -> 수강생 관리 페이지로 이동
    @GetMapping("/admin/memberInfo")
    public String showMemberInfoByAdmin(@AuthenticationPrincipal Members member,
                                        @PageableDefault(size = 10) Pageable pageable,
                                        Model model){

        Page<Members> members;
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by("memberNo").descending());
        members = adminService.findAll(pageable);

        int startPage = Math.max(1, members.getPageable().getPageNumber() - 4);
        int endPage = Math.min(members.getPageable().getPageNumber() + 4, members.getTotalPages());

        model.addAttribute("member", members);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);


        return "/page/security/memberInfoByAdmin";
    }

    // 관리자 페이지에서 개설 강의 목록 조회
    @GetMapping("/admin/memberInfo/lectures")
    public String findMembersByLectureNo(@PageableDefault(size = 10) Pageable pageable,
                                         Model model) {
        Page<LectureInfo> lectureInfo = lectureInfoService.findAll(pageable);

        int startPage = Math.max(1, lectureInfo.getPageable().getPageNumber() - 4);
        int endPage = Math.min(lectureInfo.getPageable().getPageNumber() + 4, lectureInfo.getTotalPages());

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("lectureInfo", lectureInfo); // 강의 목록 리스트 저장

        return "/page/security/adminLectureList";
    }

    // 관리자 페이지에서 개설 강의 목록 옆 '조회' 버튼 클릭 시, 수강생 목록 출력
    @GetMapping("/admin/memberInfo/lectureNo/{lectureNo}")
    public String findMemberInfoByLectureNo(@PathVariable Long lectureNo,
                                            @PageableDefault(size = 10) Pageable pageable,
                                            Model model){

        Page<Members> students = adminService.findStudentList(lectureNo, pageable);

        int startPage = Math.max(1, students.getPageable().getPageNumber() - 1);
        int endPage = Math.min(students.getPageable().getPageNumber() + 3, students.getTotalPages());

        model.addAttribute("lectureNo", lectureNo);
        model.addAttribute("member", students);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "/page/security/studentList";
    }
}