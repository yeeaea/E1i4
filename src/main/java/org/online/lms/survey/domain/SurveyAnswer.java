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
    private Long surveyAnsNo;

    @Column(name = "lecture_no")
    private Long lectureNo;

    @Column(name = "member_no")
    private Long memberNo;

    @Column(name = "survey_ques_no")
    private Long surveyQuesNo;

    @Column(name = "survey_ans_name", nullable = false)
    private String surveyAnsName;

    @Column(name = "survey_ans_score")
    private BigDecimal surveyAnsScore;

    @Builder
    public SurveyAnswer(Long surveyAnsNo,
                        Long lectureNo,
                        Long memberNo,
                        Long surveyQuesNo,
                        String surveyAnsName,
                        BigDecimal surveyAnsScore) {
        this.surveyAnsNo = surveyAnsNo;
        this.lectureNo = lectureNo;
        this.memberNo = memberNo;
        this.surveyQuesNo = surveyQuesNo;
        this.surveyAnsName = surveyAnsName;
        this.surveyAnsScore = surveyAnsScore;
    }
}
