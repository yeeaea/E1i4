package org.online.lms.survey.controller;

import lombok.extern.slf4j.Slf4j;
import org.online.lms.survey.service.SurveyAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/api/survey/result")
@Slf4j
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

    @GetMapping("/getSurveyData")
    public ResponseEntity<List<String>> getSurveyData(@RequestParam("lectureNo") Long lectureNo,
                                                      @RequestParam("surveyQuesNo") Long surveyQuesNo) {
        List<String> surveyAnswerNameList = surveyAnswerService.getSurveyAnswersWithScoresByLectureAndQuestion(lectureNo, surveyQuesNo);

        log.info(surveyAnswerNameList.toString());
        return new ResponseEntity<>(surveyAnswerNameList, HttpStatus.OK);
    }
}
