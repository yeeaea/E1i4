package org.online.lms.survey.service;

import org.online.lms.survey.domain.SurveyAnswer;
import org.online.lms.survey.domain.SurveyQuesInfo;
import org.online.lms.survey.repository.SurveyAnsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class SurveyAnswerService {

    private final SurveyAnsRepository surveyAnsRepository;

    @Autowired
    public SurveyAnswerService(SurveyAnsRepository surveyAnsRepository) {
        this.surveyAnsRepository = surveyAnsRepository;
    }


    // 강의 평가 문항 답변 항목
    public void createSurveyAnswers(SurveyQuesInfo surveyQuesInfo) {
        if (surveyQuesInfo == null) {
            throw new IllegalArgumentException("강의 문항이 존재하지 않습니다.");
        }

        String surveyQuesType = surveyQuesInfo.getSurveyQuesType();
        if ("5문항선택".equals(surveyQuesType)) {
            createSurveyAnswersForMultipleChoice(surveyQuesInfo);
        } else if ("직접입력".equals(surveyQuesType)) {
            createSurveyAnswersForOpenEnded(surveyQuesInfo);
        }
    }

    // 객관식 답변
    private void createSurveyAnswersForMultipleChoice(SurveyQuesInfo surveyQuesInfo) {
        List<String> answerNames = Arrays.asList("매우그렇다", "그렇다", "보통이다", "그렇지않다", "매우그렇지않다");
        List<Integer> answerScores = Arrays.asList(5, 4, 3, 2, 1);

        for (int i = 0; i < answerNames.size(); i++) {
            String answerName = answerNames.get(i);
            int answerScore = answerScores.get(i);

            SurveyAnswer surveyAnswer = SurveyAnswer.builder()
                    .surveyQuesNo(surveyQuesInfo)
                    .surveyAnsName(answerName)
                    .surveyAnsScore(BigDecimal.valueOf(answerScore))
                    .build();
            surveyAnsRepository.save(surveyAnswer);
        }
    }

    // 주관식 답변
    private void createSurveyAnswersForOpenEnded(SurveyQuesInfo surveyQuesInfo) {
        SurveyAnswer surveyAnswer = SurveyAnswer.builder()
                .surveyQuesNo(surveyQuesInfo)
                .surveyAnsName("직접입력")
                .build();
        surveyAnsRepository.save(surveyAnswer);
    }

}
