package org.online.lms.survey.controller;

import org.online.lms.survey.domain.SurveyQuesInfo;
import org.online.lms.survey.dto.AddSurveyQuesInfoRequest;
import org.online.lms.survey.service.SurveyQuesInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/api/survey")
public class SurveyQuesInfoApiController {

    private final SurveyQuesInfoService surveyQuesInfoService;

    @Autowired
    public SurveyQuesInfoApiController (SurveyQuesInfoService surveyQuesInfoService) {
        this.surveyQuesInfoService = surveyQuesInfoService;
    }

    // 상위 문항 추가
    @PostMapping("/add-parent-question")
    public ResponseEntity<SurveyQuesInfo> addParentQuestion(@RequestBody AddSurveyQuesInfoRequest request) {
        SurveyQuesInfo newParentQuestion = surveyQuesInfoService.addParentQuestion(request);
        if (newParentQuestion != null) {
            return ResponseEntity.ok(newParentQuestion);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // 하위 문항 추가
    @PostMapping("/add-sub-question/{parentQuesNo}")
    public ResponseEntity<SurveyQuesInfo> addSubQuestion(@RequestBody AddSurveyQuesInfoRequest request, @PathVariable Long parentQuesNo) {
        SurveyQuesInfo newSubQuestion = surveyQuesInfoService.addSubQuestion(request, parentQuesNo);
        if (newSubQuestion != null) {
            return ResponseEntity.ok(newSubQuestion);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
