package org.online.lms.survey.controller;

import org.online.lms.survey.domain.SurveyQuesInfo;
import org.online.lms.survey.service.SurveyAnswerService;
import org.online.lms.survey.service.SurveyQuesInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/lms/api/survey")
public class SurveyAnsApiController {

    private final SurveyAnswerService surveyAnswerService;
    private final SurveyQuesInfoService surveyQuesInfoService;

    @Autowired
    public SurveyAnsApiController(SurveyAnswerService surveyAnswerService,
                                  SurveyQuesInfoService surveyQuesInfoService) {
        this.surveyAnswerService = surveyAnswerService;
        this.surveyQuesInfoService = surveyQuesInfoService;
    }

    //
    @PostMapping("/submit-answer")
    public ResponseEntity<Map<String, String>> submitAnswer(@RequestParam("lectureNo") Long lectureNo,
                                                            @RequestParam("memberNo") Long memberNo,
                                                            @RequestBody Map<String, Map<String, String>> formData) {
        try {
            for (String surveyQuesNo : formData.keySet()) {
                Map<String, String> surveyData = formData.get(surveyQuesNo);
                String choice = surveyData.get("choice");
                String directInputAnswer = surveyData.get("directInputAnswer");

                // 이제 surveyQuesNo, choice, directInputAnswer를 이용해 데이터 저장 로직을 수행
                SurveyQuesInfo surveyQuesInfo = surveyQuesInfoService.getSurveyQuesInfoBySurveyQuesNo(Long.parseLong(surveyQuesNo));

                if (surveyQuesInfo == null) {
                    Map<String, String> errorResponse = new HashMap<>();
                    errorResponse.put("error", "Invalid survey question ID");
                    return ResponseEntity.badRequest().body(errorResponse);
                }

                if ("2".equals(surveyQuesInfo.getSurveyQuesType())) {
                    surveyAnswerService.createAnswerForDirectInput(lectureNo, memberNo, Long.parseLong(surveyQuesNo), directInputAnswer);
                } else if ("1".equals(surveyQuesInfo.getSurveyQuesType())) {
                    surveyAnswerService.createAnswerForChoice(lectureNo, memberNo, Long.parseLong(surveyQuesNo), choice);
                } else {
                    Map<String, String> errorResponse = new HashMap<>();
                    errorResponse.put("error", "Invalid survey question ID");
                    return ResponseEntity.badRequest().body(errorResponse);
                }
            }

            Map<String, String> response = new HashMap<>();
            response.put("message", "Answers submitted successfully");

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "An error occurred while processing the data");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
