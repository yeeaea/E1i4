package org.online.lms.boards.controller;

import lombok.RequiredArgsConstructor;

import org.online.lms.boards.service.PostService;
import org.online.lms.boards.domain.Post;
import org.online.lms.boards.dto.PostRequest;
import org.online.lms.boards.dto.PostResponse;
import org.online.lms.boards.dto.UpdatePostRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;

    // 글 등록
    @PostMapping("/api/post")
    public ResponseEntity<Post> addArticle(@RequestBody PostRequest request) {
        Post savedPost = postService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedPost);
    }

    // 글 목록 조회
    @GetMapping("/api/post")
    public ResponseEntity<List<PostResponse>> findAllPost() {
        List<PostResponse> post = postService.findAll()
                .stream()
                .map(PostResponse::new)
                .toList();
        return ResponseEntity.ok().body(post);
    }

    // 글 조회
    @GetMapping("/api/post/{postNo}")
    public ResponseEntity<PostResponse> findPost(@PathVariable Long postNo) {
        Post post = postService.findById(postNo);

        return ResponseEntity.ok().body(new PostResponse(post));
    }

    // 글 삭제
    @DeleteMapping("/api/post/{postNo}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postNo) {
        postService.delete(postNo);

        return ResponseEntity.ok().build();
    }

    // 글 수정
    @PutMapping("/api/post/{postNo}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postNo,
                                           @RequestBody UpdatePostRequest request) {

        Post updatedPost = postService.update(postNo, request);

        return ResponseEntity.ok().body(updatedPost);
    }

}