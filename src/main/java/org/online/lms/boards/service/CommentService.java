package org.online.lms.boards.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.online.lms.boards.domain.Comment;
import org.online.lms.boards.domain.Post;
import org.online.lms.boards.dto.CommentRequest;
import org.online.lms.boards.dto.UpdateCommentRequest;
import org.online.lms.boards.repository.CommentRepository;
import org.online.lms.boards.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    @Autowired
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    // 댓글 등록
    public Comment saveComment(CommentRequest commentRequest, Long postNo) {
        // postNo에 해당하는 게시글에 댓글을 저장하는 로직 구현
        Post post = postRepository.findById(postNo).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없음"));
        Comment comment = commentRequest.toEntity();
        comment.setPost(post);
        return commentRepository.save(comment);
    }


    // 댓글 조회
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }


    // 댓글 가져오기
    public Comment findByCommentNo(Long commentNo) {
        return commentRepository.findById(commentNo)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + commentNo));
    }

    // 댓글 삭제
    public void delete(Long commentNo) {

        commentRepository.deleteById(commentNo);
    }


    // 해당 게시글에 댓글목록 보여지기
    public List<Comment> getCommentsByPostNo(Long postNo) {
        return commentRepository.findByPost_PostNo(postNo);
    }

    // 댓글 수정
    @Transactional
    public Comment update(Long commentNo, UpdateCommentRequest dto) {
        Comment comment = commentRepository.findById(commentNo)
                .orElseThrow(() -> new IllegalArgumentException("수정할 댓글이 없습니다."));

        comment.setCommentRtm(LocalDateTime.now());
        comment.update(dto.getCommentContent());

        return commentRepository.save(comment);
    }

    /*

    // 댓글 작성자의 닉네임을 가져오는 메소드
    public String getLoginId(Long commentNo) {
        Comment comment = commentRepository.findById(commentNo).orElse(null);

        if (comment != null) {
            return comment.getLoginId();
        }
        return null;
    }

 */
}