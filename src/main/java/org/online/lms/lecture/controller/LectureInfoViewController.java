package org.online.lms.lecture.controller;

import lombok.RequiredArgsConstructor;
import org.online.lms.lecture.domain.LectureInfo;
import org.online.lms.lecture.dto.LectureInfoViewResponse;
import org.online.lms.lecture.service.LectureInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/lms/online")
public class LectureInfoViewController {

    private final LectureInfoService lectureInfoService;

    // 회원 : 개설 강의 목록 조회
    @GetMapping("/lecture-all")
    public String lectureInfoList(Model model) {
        List<LectureInfoViewResponse> lectureInfo = lectureInfoService.findAll().stream()
                .map(LectureInfoViewResponse::new)
                .toList();
        model.addAttribute("lectureInfo", lectureInfo); // 강의 목록 리스트 저장
        
        return "page/lecture/lectureInfoList";
    }

    // 회원 : 강의 계획서 조회
    @GetMapping("/lecture-info/{lectureNo}")
    public String lectureInfo(@PathVariable Long lectureNo, Model model) {
        LectureInfo lectureInfo = lectureInfoService.findById(lectureNo);
        model.addAttribute("lectureInfo", new LectureInfoViewResponse(lectureInfo));

        return "page/lecture/lectureInfo";
    }


}
