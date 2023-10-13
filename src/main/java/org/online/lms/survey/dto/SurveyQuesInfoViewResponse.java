package org.online.lms.survey.dto;

import lombok.Getter;
import lombok.Setter;
import org.online.lms.survey.domain.SurveyQuesInfo;

@Getter
@Setter
public class SurveyQuesInfoViewResponse {
    private Long surveyQuesNo;
    private Long surveyQuesNo2;
    private String surveyQuesName;
    private String surveyQuesType;
    private boolean surveyQuesYn;

    public SurveyQuesInfoViewResponse(SurveyQuesInfo surveyQuesInfo) {
        this.surveyQuesNo = surveyQuesInfo.getSurveyQuesNo();
        this.surveyQuesNo2 = surveyQuesInfo.getSurveyQuesNo2() != null ? surveyQuesInfo.getSurveyQuesNo2().getSurveyQuesNo() : null;
        this.surveyQuesName = surveyQuesInfo.getSurveyQuesName();
        this.surveyQuesType = surveyQuesInfo.getSurveyQuesType();
        this.surveyQuesYn = surveyQuesInfo.isSurveyQuesYn();
    }
}
