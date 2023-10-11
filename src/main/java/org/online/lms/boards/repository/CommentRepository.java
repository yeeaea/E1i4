package org.online.lms.boards.repository;

import org.online.lms.boards.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost_PostNo(Long postNo);

   // List<Comment> findByLoginId(String loginId);
}