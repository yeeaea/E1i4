package org.online.lms.survey.controller;

import lombok.RequiredArgsConstructor;
import org.online.lms.survey.domain.SurveyQuesInfo;
import org.online.lms.survey.service.SurveyAnswerService;
import org.online.lms.survey.service.SurveyQuesInfoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/survey")
public class SurveyQuesInfoViewController {

    private final SurveyQuesInfoService surveyQuesInfoService;
    private final SurveyAnswerService surveyAnswerService;
    // 강의 평가 문항 목록 조회
    @GetMapping("/ques-info")
    public String surveyQuesInfoList(@PageableDefault(size = 10) Pageable pageable,
                                     Model model) {
        // 강의 평가 문항 리스트로 가져오기
        Page<SurveyQuesInfo> surveyQuesInfoList = surveyQuesInfoService.getAllSurveyQuesInfoSortedByViewNoWithPaging(pageable);

        int startPage = Math.max(1, surveyQuesInfoList.getPageable().getPageNumber() - 4);
        int endPage = Math.min(surveyQuesInfoList.getPageable().getPageNumber() + 4, surveyQuesInfoList.getTotalPages());

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("surveyQuesInfoList", surveyQuesInfoList);

        return "/page/survey/surveyQuesUpdate";
    }

}
