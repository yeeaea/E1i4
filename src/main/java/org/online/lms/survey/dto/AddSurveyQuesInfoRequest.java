package org.online.lms.survey.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.online.lms.survey.domain.SurveyQuesInfo;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddSurveyQuesInfoRequest {
    private Long surveyQuesNo; // 평가 문항 번호
    private Long parentQuesNo; // 상위 문항 번호 (재귀)
    private List<AddSurveyQuesInfoRequest> subQuestions;
    private Long surveyQuesViewNo; // 표시 문항 번호
    private String surveyQuesName; // 문항 명
    private String surveyQuesType; // 문항 답변 유형
    private boolean surveyQuesYn; // 사용 여부

    public SurveyQuesInfo toEntity() {
        return SurveyQuesInfo.builder()
                .surveyQuesNo(surveyQuesNo)
                .parentQues(parentQuesNo != null ? SurveyQuesInfo.builder().surveyQuesNo(parentQuesNo).build() : null)
                .subQuestions(subQuestions != null ? subQuestions.stream().map(AddSurveyQuesInfoRequest::toEntity).collect(Collectors.toList()) : null)
                .surveyQuesViewNo(surveyQuesViewNo)
                .surveyQuesName(surveyQuesName)
                .surveyQuesType(surveyQuesType)
                .surveyQuesYn(surveyQuesYn)
                .build();
    }
}
