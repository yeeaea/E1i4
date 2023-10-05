package org.online.lms.boards.repository;

import org.online.lms.boards.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {

}
