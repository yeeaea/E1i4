package org.online.lms.video.repository;

import org.online.lms.video.domain.ProgressInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgressInfoRepository extends JpaRepository<ProgressInfo, Long> {
}