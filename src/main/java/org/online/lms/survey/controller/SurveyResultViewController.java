package org.online.lms.survey.controller;

import org.online.lms.lecture.domain.LectureInfo;
import org.online.lms.lecture.dto.LectureInfoViewResponse;
import org.online.lms.lecture.service.LectureInfoService;
import org.online.lms.survey.domain.SurveyAnswer;
import org.online.lms.survey.domain.SurveyQuesInfo;
import org.online.lms.survey.service.SurveyAnswerService;
import org.online.lms.survey.service.SurveyQuesInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/survey/result")
public class SurveyResultViewController {

    private final LectureInfoService lectureInfoService;
    private final SurveyAnswerService surveyAnswerService;
    private final SurveyQuesInfoService surveyQuesInfoService;


    @Autowired
    public SurveyResultViewController(LectureInfoService lectureInfoService,
                                      SurveyAnswerService surveyAnswerService,
                                      SurveyQuesInfoService surveyQuesInfoService) {
        this.lectureInfoService = lectureInfoService;
        this.surveyAnswerService = surveyAnswerService;
        this.surveyQuesInfoService = surveyQuesInfoService;
    }

    // 강의평가 리스트 조회
    @GetMapping("/lecture-all")
    public String surveyView(Model model) {
        // 개설 강의 정보 목록
        List<LectureInfoViewResponse> lectureInfo = lectureInfoService.findAll().stream()
                .map(LectureInfoViewResponse::new)
                .toList();

        model.addAttribute("lectureInfo", lectureInfo);

        return "/page/survey/surveyResultList";
    }

    @GetMapping("/{lectureNo}")
    public String surveyResultByLecture(@PathVariable Long lectureNo, Model model) {
        // 강의 번호로 강의정보 데이터 가져오기
        LectureInfo lectureInfo = lectureInfoService.getLectureInfoByLectureNo(lectureNo);

        // 강의 평가문항 데이터 가져오기
        List<SurveyQuesInfo> surveyQuesInfoList = surveyQuesInfoService.findAll();
        // 강의 번호로 강의평가 답변항목 리스트 불러오기
        List<SurveyAnswer> surveyAnswerList = surveyAnswerService.findByLectureNo(lectureNo);

        model.addAttribute("lectureInfo", lectureInfo);
        model.addAttribute("surveyQuesInfoList", surveyQuesInfoList);
        model.addAttribute("surveyAnswerList", surveyAnswerList);
        return"/page/survey/surveyResult";
    }


}
