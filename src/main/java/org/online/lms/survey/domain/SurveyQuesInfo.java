package org.online.lms.survey.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Table(name = "survey_ques_info")
@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
public class SurveyQuesInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_ques_no")
    private Long surveyQuesNo; // 평가 문항 번호

    @ManyToOne
    @JoinColumn(name = "survey_ques_no2", referencedColumnName = "survey_ques_no")
    @JsonManagedReference
    private SurveyQuesInfo parentQues; // 상위 문항 번호 (재귀)

    @OneToMany(mappedBy = "parentQues")
    @JsonBackReference
    private List<SurveyQuesInfo> subQuestions; // 하위 문항들

    @Column(name = "survey_ques_view_no")
    private Long surveyQuesViewNo; // 표시 문항 번호

    @Column(name = "survey_ques_name", nullable = false)
    private String surveyQuesName; // 문항 명

    @Column(name = "survey_ques_type")
    private String surveyQuesType; // 문항 답변 유형

    @Column(name = "survey_ques_yn", nullable = false)
    private boolean surveyQuesYn; // 사용 여부

    @Builder
    public SurveyQuesInfo(long surveyQuesNo,
                          SurveyQuesInfo parentQues,
                          List<SurveyQuesInfo> subQuestions,
                          Long surveyQuesViewNo,
                          String surveyQuesName,
                          String surveyQuesType,
                          boolean surveyQuesYn) {
        this.surveyQuesNo = surveyQuesNo;
        this.parentQues = parentQues;
        this.subQuestions = subQuestions;
        this.surveyQuesViewNo = surveyQuesViewNo;
        this.surveyQuesName = surveyQuesName;
        this.surveyQuesType = surveyQuesType;
        this.surveyQuesYn = surveyQuesYn;
    }


}
