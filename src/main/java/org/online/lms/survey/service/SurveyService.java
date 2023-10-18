package org.online.lms.survey.service;

import org.online.lms.survey.domain.Survey;
import org.online.lms.survey.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SurveyService {

    private final SurveyRepository surveyRepository;

    @Autowired
    public SurveyService(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    public Survey createSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }

    // 중복 강의평가 확인
    public boolean isDuplicate(Long lectureNo, Long memberNo) {
        return surveyRepository.isDuplicate(lectureNo, memberNo);
    }

}
