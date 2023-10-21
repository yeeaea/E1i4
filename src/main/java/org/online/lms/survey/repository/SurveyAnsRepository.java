package org.online.lms.survey.repository;

import jakarta.transaction.Transactional;
import org.online.lms.survey.domain.SurveyAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SurveyAnsRepository extends JpaRepository<SurveyAnswer, Long> {

    // 강의 번호로 강의평가 답변항목 리스트 가져오기
    List<SurveyAnswer> findByLectureNo(Long lectureNo);

    // 강의 번호로 해당 강의번호로 저장된 답변항목이 있는지 확인
    boolean existsByLectureNo(Long lectureNo);

    // 여러 강의 번호에 대한 데이터 삭제
    @Transactional
    void deleteByLectureNoIn(List<Long> lectureNos);

    // surveyAnsScore 필드가 존재하고, 강의번호와 평가문항번호를 이용하여 답변명을 얻어오는 메서드
    @Query("SELECT s.surveyAnsName FROM SurveyAnswer s " +
            "WHERE s.lectureNo = :lectureNo " +
            "AND s.surveyQuesNo = :surveyQuesNo " +
            "AND s.surveyAnsScore IS NOT NULL")
    List<String> findSurveyAnsNameByLectureAndQuestion(
            @Param("lectureNo") Long lectureNo,
            @Param("surveyQuesNo") Long surveyQuesNo
    );


}
