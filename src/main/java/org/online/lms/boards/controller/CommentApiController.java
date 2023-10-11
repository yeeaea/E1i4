package org.online.lms.boards.controller;

import lombok.RequiredArgsConstructor;
import org.online.lms.boards.domain.Comment;
import org.online.lms.boards.dto.CommentRequest;
import org.online.lms.boards.dto.CommentResponse;
import org.online.lms.boards.dto.UpdateCommentRequest;
import org.online.lms.boards.service.CommentService;
import org.online.lms.security.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentApiController {
    // private final MemberService memberService;
    private final CommentService commentService;

// 댓글 등록
    @PostMapping("/api/comments")
    public ResponseEntity<CommentResponse> addComment(@RequestBody CommentRequest dto) {

            Comment savedComment = commentService.saveComment(dto, dto.getPostNo());

            return ResponseEntity.status(HttpStatus.CREATED).body(new CommentResponse(savedComment));

    }

    // 댓글 리스트
    @GetMapping("/api/comments")
    public ResponseEntity<List<CommentResponse>> findAllComments() {
        List<CommentResponse> comments = commentService.findAll().stream().map(CommentResponse::new)
                .toList();

        return ResponseEntity.ok().body(comments);
    }

    // 댓글 가져오기
    @GetMapping("/api/comments/{commentNo}")
    public ResponseEntity<CommentResponse> Comment(@PathVariable Long commentNo) {
        Comment comment = commentService.findByCommentNo(commentNo);

        return ResponseEntity.ok().body(new CommentResponse(comment));
    }

    // 댓글 삭제
    @DeleteMapping("/api/comments/{commentNo}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentNo) {
        commentService.delete(commentNo);

        return ResponseEntity.ok().build();
    }


    // 댓글 수정
    @PutMapping("/api/comments/{commentNo}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long commentNo,
                                                         @RequestBody UpdateCommentRequest dto) {
      Comment updatedComment = commentService.update(commentNo, dto);

        return ResponseEntity.ok().body(updatedComment);
    }
}
