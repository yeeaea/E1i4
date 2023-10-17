package org.online.lms.survey.controller;

import org.online.lms.survey.domain.SurveyQuesInfo;
import org.online.lms.survey.service.SurveyQuesInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<SurveyQuesInfo> addQuestion(@RequestParam(value = "surveyQuesName") String surveyQuesName,
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

    // 강의 평가 문항 수정
    @PutMapping("/update-question/{surveyQuesNo}")
    public ResponseEntity<SurveyQuesInfo> updateQuestion(@PathVariable Long surveyQuesNo,
                                                         @RequestParam(value = "surveyQuesName") String surveyQuesName,
                                                         @RequestParam(value = "surveyQuesViewNo") Long surveyQuesViewNo,
                                                         @RequestParam(value = "surveyQuesYn") boolean surveyQuesYn) {
        SurveyQuesInfo updatedQuestion = surveyQuesInfoService.updateQuestion(surveyQuesNo,
                                                                              surveyQuesName,
                                                                              surveyQuesViewNo,
                                                                              surveyQuesYn);
        if (updatedQuestion != null) {
            return ResponseEntity.ok(updatedQuestion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 강의 평가 문항 삭제
    @PostMapping("/delete")
    public ResponseEntity<String> deleteQuestions(@RequestBody List<Long> surveyQuesNos) {
        // surveyQuesNos 목록에 포함된 강의 평가 목록 삭제
        boolean success = surveyQuesInfoService.deleteQuestions(surveyQuesNos);

        if (success) {
            return new ResponseEntity<>("강의 평가 문항이 삭제되었습니다", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("강의 평가 문항 삭제 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
