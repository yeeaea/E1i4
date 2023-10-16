package org.online.lms.survey.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SurveyQuesInfoViewResponse {
    private Long surveyQuesNo; // 평가 문항 번호
    private Long surveyQuesNo2; // 상위 평가 문항 번호(재귀)
    private Long surveyQuesViewNo; // 표시 문항 번호
    private String surveyQuesName; // 문항명
    private String surveyQuesType; // 문항 답변 유형
    private boolean surveyQuesYn; // 사용 여부


}
