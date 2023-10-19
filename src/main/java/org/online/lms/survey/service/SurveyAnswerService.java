package org.online.lms.survey.service;

import org.online.lms.survey.domain.SurveyAnswer;
import org.online.lms.survey.repository.SurveyAnsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SurveyAnswerService {

    private final SurveyAnsRepository surveyAnsRepository;

    @Autowired
    public SurveyAnswerService(SurveyAnsRepository surveyAnsRepository) {
        this.surveyAnsRepository = surveyAnsRepository;
    }

    // 답변 항목 객체 생성 - 5문항선택
    public void createAnswerForChoice(Long lectureNo, Long memberNo, Long surveyQuesNo, String choice) {
        SurveyAnswer answer = SurveyAnswer.builder()
                .lectureNo(lectureNo)
                .memberNo(memberNo)
                .surveyQuesNo(surveyQuesNo)
                .surveyAnsName(choice)
                .surveyAnsScore(mapChoiceToScore(choice))
                .build();

        surveyAnsRepository.save(answer);
    }

    // 답변 항목 객체 생성 - 직접입력
    public void createAnswerForDirectInput(Long lectureNo, Long memberNo, Long surveyQuesNo, String directInputAnswer) {
        SurveyAnswer answer = SurveyAnswer.builder()
                .lectureNo(lectureNo)
                .memberNo(memberNo)
                .surveyQuesNo(surveyQuesNo)
                .surveyAnsName(directInputAnswer)
                .build();

        surveyAnsRepository.save(answer);
    }

    // 5문항 선택의 경우 점수 배점
    private BigDecimal mapChoiceToScore(String choice) {
        switch (choice) {
            case "매우 그렇다":
                return new BigDecimal(5);
            case "그렇다":
                return new BigDecimal(4);
            case "보통이다":
                return new BigDecimal(3);
            case "그렇지 않다":
                return new BigDecimal(2);
            case "매우 그렇지 않다":
                return new BigDecimal(1);
            default:
                throw new IllegalArgumentException("Invalid choice");
        }
    }
}