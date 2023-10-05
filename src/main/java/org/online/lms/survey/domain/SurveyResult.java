package org.online.lms.survey.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.online.lms.lecture.domain.LectureInfo;
import org.online.lms.security.domain.Members;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;

@Table(name="survey_result")
@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SurveyResult {

    // 데이터베이스에 대한 추가 및 갱신에 영향을 주지 않음 = false
    // 데이터베이스에 대한 추가 및 갱신에 영향을 줌 = true
    @Id
    @OneToOne
    @JoinColumn(name = "survey_no", referencedColumnName = "survey_no"
                /*insertable = false, updatable = false*/)
    private Survey surveyNo;

    @OneToOne
    @JoinColumn(name = "lecture_no", referencedColumnName = "lecture_no"
                /*insertable = false, updatable = false*/)
    private Survey lectureNo;

    @OneToOne
    @JoinColumn(name = "member_no", referencedColumnName = "member_no"
            /*insertable = false, updatable = false*/)
    private Survey memberNo; // 회원 번호 (외래키 연결)

    @OneToOne
    @JoinColumn(name = "survey_ques_no", referencedColumnName = "survey_ques_no"
            /*insertable = false, updatable = false*/)
    private SurveyQuesInfo surveyQuesNo; // 평가 문항 번호 (외래키 연결)

    @Column(name = "survey_ans_no", nullable = false)
    private Long surveyAnsNo; // 답변 문항 번호

    @Column(name = "survey_score", nullable = false)
    private BigDecimal surveyScore; // 점수

    @Column(name = "survey_sub_ans")
    private String surveySubAns; // 주관식 답변 내용

    @Builder
    public SurveyResult(Survey surveyNo,
                        Survey lectureNo,
                        Survey memberNo,
                        SurveyQuesInfo surveyQuesNo,
                        Long surveyAnsNo,
                        BigDecimal surveyScore,
                        String surveySubAns) {
        this.surveyNo = surveyNo;
        this.lectureNo = lectureNo;
        this.memberNo = memberNo;
        this.surveyQuesNo = surveyQuesNo;
        this.surveyAnsNo = surveyAnsNo;
        this.surveyScore = surveyScore;
        this.surveySubAns = surveySubAns;
    }
}
