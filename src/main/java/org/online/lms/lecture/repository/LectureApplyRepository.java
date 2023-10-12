package org.online.lms.lecture.repository;

import org.online.lms.lecture.domain.LectureApply;
import org.online.lms.security.domain.Members;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureApplyRepository extends JpaRepository<LectureApply, Long> {

    // 사용자 이름 (memberName)을 기반으로 수강 신청 목록을 검색
    List<LectureApply> findByMemberNo_MemberNo(Long memberNo);

}
