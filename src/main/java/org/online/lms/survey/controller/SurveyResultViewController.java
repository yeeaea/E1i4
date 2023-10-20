package org.online.lms.survey.controller;

import org.online.lms.lecture.dto.LectureInfoViewResponse;
import org.online.lms.lecture.service.LectureInfoService;
import org.online.lms.survey.domain.SurveyAnswer;
import org.online.lms.survey.service.SurveyAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/survey/result")
public class SurveyResultViewController {

    private final LectureInfoService lectureInfoService;
    private final SurveyAnswerService surveyAnswerService;


    @Autowired
    public SurveyResultViewController(LectureInfoService lectureInfoService,
                                      SurveyAnswerService surveyAnswerService) {
        this.lectureInfoService = lectureInfoService;
        this.surveyAnswerService = surveyAnswerService;
    }

    // 강의평가 리스트 조회
    @GetMapping("/lecture-all")
    public String surveyView(Model model) {
        // 개설 강의 정보 목록
        List<LectureInfoViewResponse> lectureInfo = lectureInfoService.findAll().stream()
                .map(LectureInfoViewResponse::new)
                .toList();

        model.addAttribute("lectureInfo", lectureInfo); // 강의 목록 리스트 저장

        return "/page/survey/surveyResultList";
    }

    // 강의 별 강의평가 조회
    @GetMapping("/{lectureNo}")
    public String surveyResultByLecture(@PathVariable Long lectureNo, Model model) {
        List<SurveyAnswer> surveyResult = surveyAnswerService.getSurveyAnswersByLectureNo(lectureNo);

        model.addAttribute("surveyResult", surveyResult);

        return "/page/survey/surveyResult";
    }

}
