package org.online.lms.lecture.repository;

import org.online.lms.lecture.domain.LectureInfo;
import org.online.lms.lecture.dto.MyLectureInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface LectureInfoRepository extends JpaRepository<LectureInfo, Long> {

    // 강의 번호 목록에 해당하는 강의 삭제
    void deleteByLectureNoIn(List<Long> lectureNos);

    // 회원번호로 강의과정, 강의제목, 강의 시작일, 강의 종료일, 수료여부 가져오기
    @Query("SELECT new org.online.lms.lecture.dto.MyLectureInfoDto(li.lectureCourse, li.lectureTitle, li.lectureStartAt, li.lectureEndAt, la.completionYn) " +
            "FROM LectureInfo li " +
            "JOIN LectureApply la ON la.lectureNo = li.lectureNo " +
            "WHERE la.memberNo = :memberNo")
    List<MyLectureInfoDto> findMyLectureInfoByMemberNo(@Param("memberNo") Long memberNo);


}
