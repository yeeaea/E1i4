package org.online.lms.boards.repository;

import org.online.lms.boards.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    Page<Post> findByPostTitleContaining(String keyword, Pageable pageable);
    Page<Post> findAllByOrderByPostViewDesc(Pageable pageable);
    Page<Post> findByBoardNo(Long boardNo, Pageable pageable);
    Page<Post> findAllByBoardNoOrderByPostViewDesc(Long boardNo, Pageable descendingPageable);
}
