package org.online.lms.lecture.controller;

import org.online.lms.lecture.domain.LectureInfo;
import org.online.lms.lecture.dto.AddLectureInfoRequest;
import org.online.lms.lecture.service.LectureInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/online")
public class LectureInfoUpdateController {

    private final LectureInfoService lectureInfoService;

    @Autowired
    public LectureInfoUpdateController(LectureInfoService lectureInfoService) {
        this.lectureInfoService = lectureInfoService;
    }

    // 관리자 : 강의 목록 조회 + 등록 + 수정 + 삭제
    @GetMapping("/lecture-all")
    public String lectureList(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<LectureInfo> lectureInfos = lectureInfoService.findAll(pageable);

        int startPage = Math.max(1, lectureInfos.getPageable().getPageNumber() - 1);
        int endPage = Math.min(lectureInfos.getPageable().getPageNumber() + 3, lectureInfos.getTotalPages());

        model.addAttribute("lectureInfo", lectureInfos);
        model.addAttribute("lectureInfoRequest", new AddLectureInfoRequest());
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "/page/lecture/lectureUpdate";
    }
}