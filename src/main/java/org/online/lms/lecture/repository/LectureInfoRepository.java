package org.online.lms.lecture.repository;

import org.online.lms.lecture.domain.LectureInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureInfoRepository extends JpaRepository<LectureInfo, Long> {

}
