package org.online.lms.boards.controller;

import lombok.RequiredArgsConstructor;

import org.online.lms.boards.domain.Post;
import org.online.lms.boards.dto.PostRequest;
import org.online.lms.boards.dto.PostResponse;
import org.online.lms.boards.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class MaterialApiController {

    private final PostService postService;

    // 글 등록
    @PostMapping("/api/material")
    public ResponseEntity<Post> addArticle(@RequestParam("postTitle") String postTitle,
                                           @RequestParam("postContent") String postContent,
                                           @RequestParam(value = "file1", required = false) MultipartFile file1,
                                           Principal principal) throws Exception {

        if (principal != null) {
            // 현재 로그인한 사용자의 아이디
            String loginId = principal.getName();

            PostRequest dto = new PostRequest();
            dto.setPostTitle(postTitle);
            dto.setPostContent(postContent);
            dto.setLoginId(loginId);
            dto.setBoardNo(3L);
            dto.setBoardType("강의자료");

            Post savedPost = postService.save(dto, file1);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(savedPost);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    // 글 목록 조회
    @GetMapping("/api/material")
    public ResponseEntity<List<PostResponse>> findAllPost() {
        List<PostResponse> post = postService.findAll(null)
                .stream()
                .map(PostResponse::new)
                .toList();
        return ResponseEntity.ok().body(post);
    }

    // 글 조회
    @GetMapping("/api/material/{postNo}")
    public ResponseEntity<PostResponse> findPost(@PathVariable Long postNo) {
        Post post = postService.findById(postNo);

        return ResponseEntity.ok().body(new PostResponse(post));
    }

    // 글 삭제
    @DeleteMapping("/api/material/{postNo}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postNo) {
        postService.delete(postNo);

        return ResponseEntity.ok().build();
    }

    // 글 수정
    @PutMapping("/api/material/{postNo}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postNo,
                                           @RequestParam("postTitle") String postTitle,
                                           @RequestParam("postContent") String postContent,
                                           @RequestParam(name = "file1", required = false) MultipartFile file1,
                                           Principal principal) throws Exception {

        // 현재 로그인한 사용자 아이디
        String currentLoginId = principal.getName();

        Post post = postService.findById(postNo);

        if (post == null) {
            // 글이 존재하지 않는 경우 에러 처리
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // 글 작성자의 아이디
        String authorId = post.getLoginId();

        // 현재 사용자와 글 작성자를 비교하여 권한 확인
        if (!currentLoginId.equals(authorId)) {
            // 현재 사용자가 글 작성자가 아닌 경우 권한이 없으므로 에러 처리
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        post.setPostTitle(postTitle);
        post.setPostContent(postContent);

        Post updatedPost = postService.update(postNo, postTitle, postContent, file1);

        return ResponseEntity.ok().body(updatedPost);
    }
}
