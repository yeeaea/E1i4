package org.online.lms.lecture.repository;

import org.online.lms.lecture.domain.LectureApply;
import org.online.lms.security.domain.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LectureApplyRepository extends JpaRepository<LectureApply, Long> {

    // 사용자 이름 (memberName)을 기반으로 수강 신청 목록을 검색
    List<LectureApply> findByMemberNo(Long memberNo);

    // 강의 번호와 회원 번호를 기반으로 중복 수강 신청 여부를 확인
    @Query("SELECT CASE WHEN COUNT(la) > 0 THEN true ELSE false END FROM LectureApply la WHERE la.lectureNo = :lectureNo AND la.memberNo = :memberNo")
    boolean isDuplicate(@Param("lectureNo") Long lectureNo, @Param("memberNo") Long memberNo);
}
