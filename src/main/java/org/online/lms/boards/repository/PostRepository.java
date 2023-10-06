package org.online.lms.boards.repository;

import org.online.lms.boards.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {

    Page<Post> findAll(Pageable pageable);
    Page<Post> findByPostTitleContaining(String keyword, Pageable pageable);
    Page<Post> findAllByOrderByPostViewDesc(Pageable pageable);
}
