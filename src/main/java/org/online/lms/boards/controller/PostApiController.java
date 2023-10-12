package org.online.lms.boards.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.online.lms.boards.service.PostService;
import org.online.lms.boards.domain.Post;
import org.online.lms.boards.dto.PostRequest;
import org.online.lms.boards.dto.PostResponse;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;

    // 글 등록
    @PostMapping("/api/post")
    public ResponseEntity<Post> addArticle(@RequestParam("postTitle") String postTitle,
                                           @RequestParam("postContent") String postContent,
                                           @RequestParam(value = "file1", required = false) MultipartFile file1,
                                           Principal principal,
                                           HttpServletRequest request) throws Exception {

        if (principal != null) {
            // 현재 로그인한 사용자의 아이디
            String loginId = principal.getName();

            PostRequest dto = new PostRequest();
            dto.setPostTitle(postTitle);
            dto.setPostContent(postContent);
            dto.setLoginId(loginId);
            dto.setBoardNo(1L);
            dto.setBoardType("질의응답");

            Post savedPost = postService.save(dto, file1);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(savedPost);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    // 글 목록 조회
    @GetMapping("/api/post")
    public ResponseEntity<List<PostResponse>> findAllPost() {
        List<PostResponse> post = postService.findAll(null)
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

    // 글 수정 시 파일 삭제
    @PostMapping("/api/deleteFile")
    public ResponseEntity<String> deleteFile(@RequestParam Long postNo) {
        try {
            postService.deleteFileAndSetNull(postNo);
            return ResponseEntity.ok("파일이 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 삭제 중 오류가 발생했습니다.");
        }
    }

    private final String fileStoragePath = System.getProperty("user.dir") + "/src/main/resources/static/files/";

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        // 파일 경로 생성
        Path filePath = Paths.get(fileStoragePath).resolve(fileName);
        Resource resource;

        try {
            resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                // 파일을 찾을 수 없거나 읽을 수 없음
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            // 파일 다운로드 중 오류 발생
            return ResponseEntity.status(500).build();
        }
    }
}