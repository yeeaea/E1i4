package org.online.lms.survey.service;

import org.online.lms.survey.domain.SurveyQuesInfo;
import org.online.lms.survey.repository.SurveyQuesInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyQuesInfoService {

    private final SurveyQuesInfoRepository surveyQuesInfoRepository;

    @Autowired
    public SurveyQuesInfoService(SurveyQuesInfoRepository surveyQuesInfoRepository) {
        this.surveyQuesInfoRepository = surveyQuesInfoRepository;
    }

    public List<SurveyQuesInfo> getAllSurveyQuesInfo() {
        return surveyQuesInfoRepository.findAll();
    }

    public SurveyQuesInfo getSurveyQuesInfoBySurveyQuesNo(Long surveyQuesNo) {
        return surveyQuesInfoRepository.findById(surveyQuesNo).orElse(null);
    }
}
