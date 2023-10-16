package org.online.lms.lecture.repository;

import org.online.lms.lecture.domain.LectureApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LectureApplyRepository extends JpaRepository<LectureApply, Long> {

    // 강의 번호와 회원 번호를 기반으로 중복 수강 신청 여부를 확인
    @Query("SELECT CASE WHEN COUNT(la) > 0 THEN true ELSE false END FROM LectureApply la WHERE la.lectureNo = :lectureNo AND la.memberNo = :memberNo")
    boolean isDuplicate(@Param("lectureNo") Long lectureNo, @Param("memberNo") Long memberNo);
    // true = 중복, false = 중복 아님

    // 회원 번호로 수강중인 강의 번호 받아오기
    @Query("SELECT la.lectureNo FROM LectureApply la WHERE la.memberNo = :memberNo")
    List<Long> findLectureNumbersByMemberNo(Long memberNo);

    // 강의 번호로 수강신청 객체 가져오기
    List<LectureApply> findByLectureNoIn(List<Long> lectureNos);

}
