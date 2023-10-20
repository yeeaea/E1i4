package org.online.lms.survey.controller;

import org.online.lms.survey.service.SurveyAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/api/survey/result")
public class SurveyResultApiController {

    private final SurveyAnswerService surveyAnswerService;

    @Autowired
    public SurveyResultApiController(SurveyAnswerService surveyAnswerService) {
        this.surveyAnswerService = surveyAnswerService;
    }

    @GetMapping("/check")
    public ResponseEntity<Object> checkSurveyResult(@RequestParam Long lectureNo) {
        // 강의 번호로 평가 결과를 체크
        boolean resultExists = surveyAnswerService.surveyResultExistsForLecture(lectureNo);

        // 결과가 존재하는지 여부를 반환
        return ResponseEntity.ok(resultExists ? "ResultExists" : "NoResult");
    }
}
