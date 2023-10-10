package org.online.lms.lecture.repository;

import org.online.lms.lecture.domain.LectureInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureInfoRepository extends JpaRepository<LectureInfo, Long> {

    // 강의 번호 목록에 해당하는 강의 삭제
    void deleteByLectureNoIn(List<Long> lectureNos);
}
