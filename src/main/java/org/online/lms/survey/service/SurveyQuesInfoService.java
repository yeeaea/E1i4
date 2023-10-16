package org.online.lms.survey.service;

import org.online.lms.survey.domain.SurveyQuesInfo;
import org.online.lms.survey.dto.AddSurveyQuesInfoRequest;
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
    // 강의 평가 문항 전체 조회
    public List<SurveyQuesInfo> getAllSurveyQuesInfo() {
        return surveyQuesInfoRepository.findAll();
    }

    // 강의 평가 문항 번호로 강의 평가 문항 데이터 찾기
    public SurveyQuesInfo getSurveyQuesInfoBySurveyQuesNo(Long surveyQuesNo) {
        return surveyQuesInfoRepository.findById(surveyQuesNo).orElse(null);
    }


    // 부모 문항 생성
    public SurveyQuesInfo addParentQuestion(AddSurveyQuesInfoRequest request) {
        SurveyQuesInfo surveyQuesInfo = request.toEntity();
        return surveyQuesInfoRepository.save(surveyQuesInfo);
    }

    // 하위 문항 생성
    public SurveyQuesInfo addSubQuestion(AddSurveyQuesInfoRequest request, Long parentQuesNo) {
        SurveyQuesInfo parentQuestion = surveyQuesInfoRepository.findById(parentQuesNo).orElse(null);
        if (parentQuestion != null) {
            SurveyQuesInfo subQuestion = request.toEntity();
            return surveyQuesInfoRepository.save(subQuestion);
        }

        return null; // 부모 문항이 존재하지 않으면 null 반환
    }
}
