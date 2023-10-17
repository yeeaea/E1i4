package org.online.lms.survey.repository;

import org.online.lms.survey.domain.SurveyQuesInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurveyQuesInfoRepository extends JpaRepository<SurveyQuesInfo, Long> {

    // 강의 평가 문항 번호 목록에 해당하는 문항 삭제
    void deleteBySurveyQuesNoIn(List<Long> surveyQuesNos);
}
