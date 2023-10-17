package org.online.lms.security.repository;

import org.online.lms.security.domain.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Members, Long> {

    // 관리자 페이지에서 수강생 목록 출력
    @Query("SELECT m FROM Members m WHERE m.memberRole = 'USER'")
    List<Members> findAll();

    // 관리자 페이지에서 수강생 목록 출력
    @Query("SELECT m.memberName, m.loginId, m.memberEmail, m.memberTel FROM Members m JOIN LectureApply la ON m.memberNo = la.memberNo WHERE la.lectureNo = :lectureNo")
    List<Members> findMembersByLectureNo(@Param("lectureNo") Long lectureNo);

}
