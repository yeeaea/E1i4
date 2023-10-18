package org.online.lms.survey.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Table(name="survey_answer")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SurveyAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_ans_no")
    private long surveyAnsNo;

    // 데이터베이스에 대한 추가 및 갱신에 영향을 주지 않음 = false
    // 데이터베이스에 대한 추가 및 갱신에 영향을 줌 = true
    @OneToOne
    @JoinColumn(name = "survey_ques_no", referencedColumnName = "survey_ques_no"
                /*insertable = false, updatable = false*/)
    private SurveyQuesInfo surveyQuesNo;

    @Column(name = "survey_ans_name", nullable = false)
    private String surveyAnsName;

    @Column(name = "survey_ans_score")
    private BigDecimal surveyAnsScore;

    @Builder
    public SurveyAnswer(long surveyAnsNo,
                        SurveyQuesInfo surveyQuesNo,
                        String surveyAnsName,
                        BigDecimal surveyAnsScore) {
        this.surveyAnsNo = surveyAnsNo;
        this.surveyQuesNo = surveyQuesNo;
        this.surveyAnsName = surveyAnsName;
        this.surveyAnsScore = surveyAnsScore;
    }
}
