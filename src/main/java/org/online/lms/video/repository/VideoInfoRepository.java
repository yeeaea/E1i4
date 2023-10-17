package org.online.lms.video.repository;

import org.online.lms.video.domain.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoInfoRepository extends JpaRepository<Content, Long>{
    //Content findByYtbUrl(String ytbUrl);

    // 테이블의 정보(값) 받아오기
    Page<Content> findAll(Pageable pageable);
}
