package org.online.lms.survey.service;

import org.online.lms.survey.domain.SurveyAnswer;
import org.online.lms.survey.repository.SurveyAnsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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


    // 강의 번호로 저장된 강의평가 데이터가 있는지 확인
    public boolean surveyResultExistsForLecture(Long lectureNo) {
        return surveyAnsRepository.existsByLectureNo(lectureNo);
    }

    // 강의 번호로 강의평가 답변항목 리스트 불러오기
    public List<SurveyAnswer> findByLectureNo(Long lectureNo) {
        return surveyAnsRepository.findByLectureNo(lectureNo);
    }


    public List<String> getSurveyAnswersWithScoresByLectureAndQuestion(Long lectureNo, Long surveyQuesNo) {
        return surveyAnsRepository.findSurveyAnsNameByLectureAndQuestion(lectureNo, surveyQuesNo);
    }
}
