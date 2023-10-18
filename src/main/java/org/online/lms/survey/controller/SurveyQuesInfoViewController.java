package org.online.lms.survey.controller;

import lombok.RequiredArgsConstructor;
import org.online.lms.survey.domain.SurveyQuesInfo;
import org.online.lms.survey.service.SurveyAnswerService;
import org.online.lms.survey.service.SurveyQuesInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/survey")
public class SurveyQuesInfoViewController {

    private final SurveyQuesInfoService surveyQuesInfoService;
    private final SurveyAnswerService surveyAnswerService;
    // 강의 평가 문항 목록 조회
    @GetMapping("/ques-info")
    public String surveyQuesInfoList(Model model) {
        // 강의 평가 문항 리스트로 가져오기
        List<SurveyQuesInfo> surveyQuesInfoList = surveyQuesInfoService.getAllSurveyQuesInfo();

        model.addAttribute("surveyQuesInfoList", surveyQuesInfoList);

        return "/page/survey/surveyQuesUpdate";
    }

}
