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

    // 강의 평가 문항 추가
    @PostMapping("/add-questions")
    public ResponseEntity<SurveyQuesInfo> addSubQuestion(@RequestParam(value = "surveyQuesName") String surveyQuesName,
                                                         @RequestParam(value = "surveyQuesType", required = false) String surveyQuesType,
                                                         @RequestParam(value = "surveyQuesViewNo") Long surveyQuesViewNo,
                                                         @RequestParam(value = "surveyQuesYn") boolean surveyQuesYn,
                                                         @RequestParam(value = "parentQuesNo", required = false) Long parentQuesNo) {
        SurveyQuesInfo subQuesInfo = surveyQuesInfoService.addQuestion(surveyQuesName,
                                                                       surveyQuesType,
                                                                       surveyQuesViewNo,
                                                                       surveyQuesYn,
                                                                       parentQuesNo);

        return ResponseEntity.ok(subQuesInfo);
    }

}
