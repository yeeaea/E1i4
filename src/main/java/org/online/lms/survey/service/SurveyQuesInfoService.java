package org.online.lms.survey.service;

import org.online.lms.survey.domain.SurveyQuesInfo;
import org.online.lms.survey.repository.SurveyQuesInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
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

        List<SurveyQuesInfo> surveyQuesInfoList = surveyQuesInfoRepository.findAll();

        // 강의 평가 문항 표시 번호 순으로 정렬
        Collections.sort(surveyQuesInfoList, new Comparator<SurveyQuesInfo>() {
            @Override
            public int compare(SurveyQuesInfo q1, SurveyQuesInfo q2) {
                return Long.compare(q1.getSurveyQuesViewNo(), q2.getSurveyQuesViewNo());
            }
        });

        return surveyQuesInfoList;
    }

    // 강의 평가 문항 번호로 강의 평가 문항 데이터 찾기
    public SurveyQuesInfo getSurveyQuesInfoBySurveyQuesNo(Long surveyQuesNo) {
        return surveyQuesInfoRepository.findById(surveyQuesNo).orElse(null);
    }

    // 강의 평가 문항 추가
    public SurveyQuesInfo addQuestion(String surveyQuesName,
                                         String surveyQuesType,
                                         Long surveyQuesViewNo,
                                         boolean surveyQuesYn,
                                         Long parentQuesNo) {
        SurveyQuesInfo surveyQuesInfo = new SurveyQuesInfo();
        surveyQuesInfo.setSurveyQuesName(surveyQuesName);
        surveyQuesInfo.setSurveyQuesType(surveyQuesType);
        surveyQuesInfo.setSurveyQuesViewNo(surveyQuesViewNo);
        surveyQuesInfo.setSurveyQuesYn(surveyQuesYn);

        if (parentQuesNo != null) {
            SurveyQuesInfo parentQuestion = surveyQuesInfoRepository.findById(parentQuesNo).orElse(null);

            if (parentQuestion !=null) {
                surveyQuesInfo.setParentQues(parentQuestion);
            }
        }

        return surveyQuesInfoRepository.save(surveyQuesInfo);
    }

    // 강의 평가 문항 수정 (문항명, 정렬 순서, 사용 여부만 수정 가능)
    public SurveyQuesInfo updateQUestion(Long surveyQuesNo,
                                         String surveyQuesName,
                                         Long surveyQuesViewNo,
                                         boolean surveyQuesYn) {
        // 기존 문항 불러오기
        SurveyQuesInfo existingQuestion = surveyQuesInfoRepository.findById(surveyQuesNo).orElse(null);

        if (existingQuestion != null) {
            // 수정할 정보로 업데이트
            existingQuestion.setSurveyQuesName(surveyQuesName);
            existingQuestion.setSurveyQuesViewNo(surveyQuesViewNo);
            existingQuestion.setSurveyQuesYn(surveyQuesYn);

            // 수정된 문항 저장
            return surveyQuesInfoRepository.save(existingQuestion);
        }

        // 수정 대상 문항을 찾지 못한 경우
        return null;
    }
}
