package org.online.lms.survey.controller;

import org.online.lms.survey.domain.Survey;
import org.online.lms.survey.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/lms/api/survey")
public class MySurveyApiController {

    private final SurveyService surveyService;

    @Autowired
    public MySurveyApiController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @PostMapping
    public ResponseEntity<Survey> createSurvey(@RequestBody Survey survey) {
        Survey createdSurvey = surveyService.createSurvey(survey);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSurvey);
    }

    // 중복 강의평가 확인
    @GetMapping("/check-duplicate-survey")
    public ResponseEntity<Object> checkDuplicateSurvey(@RequestParam Long lectureNo,
                                                       @RequestParam Long memberNo) {
        boolean isDuplicate = surveyService.isDuplicate(lectureNo, memberNo);
        Map<String, Object> response = new HashMap<>();
        response.put("isDuplicate", isDuplicate);
        return ResponseEntity.ok(response);
    }
}
