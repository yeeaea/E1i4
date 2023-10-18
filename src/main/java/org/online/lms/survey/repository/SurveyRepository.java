package org.online.lms.survey.repository;

import org.online.lms.survey.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
    // 강의 번호와 회원 번호로 중복 강의평가여부 확인
    @Query("SELECT CASE WHEN COUNT(survey) > 0 THEN true ELSE false END FROM Survey survey WHERE survey.lectureNo = :lectureNo AND survey.memberNo = :memberNo")
    boolean isDuplicate(@Param("lectureNo") Long lectureNo, @Param("memberNo") Long memberNo);


}
