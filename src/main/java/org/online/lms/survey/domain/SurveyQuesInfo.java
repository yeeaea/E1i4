package org.online.lms.survey.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table(name = "survey_ques_info")
@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SurveyQuesInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_ques_no")
    private long surveyQuesNo; // 평가 문항 번호

    @OneToOne
    @JoinColumn(name = "survey_ques_no2", referencedColumnName = "survey_ques_no")
    private SurveyQuesInfo surveyQuesNo2; // 상위 문항 번호 (재귀)

    @Column(name = "survey_ques_view_no")
    private Long surveyQuesViewNo; // 표시 문항 번호

    @Column(name = "survey_ques_name", nullable = false)
    private String surveyQuesName; // 문항 명

    @Column(name = "survey_ques_type", nullable = false)
    private String surveyQuesType; // 문항 답변 유형

    @Column(name = "survey_ques_yn", nullable = false)
    private boolean surveyQuesYn; // 사용 여부

    @Builder
    public SurveyQuesInfo(long surveyQuesNo,
                          SurveyQuesInfo surveyQuesNo2,
                          String surveyQuesName,
                          String surveyQuesType,
                          boolean surveyQuesYn) {
        this.surveyQuesNo = surveyQuesNo;
        this.surveyQuesNo2 = surveyQuesNo2;
        this.surveyQuesName = surveyQuesName;
        this.surveyQuesType = surveyQuesType;
        this.surveyQuesYn = surveyQuesYn;
    }
}
