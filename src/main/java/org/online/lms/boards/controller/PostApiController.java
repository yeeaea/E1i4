package org.online.lms.boards.controller;

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

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;

    // 글 등록

    @PostMapping("/api/post")
    public ResponseEntity<Post> addArticle(@RequestParam("postTitle") String postTitle,
                                           @RequestParam("postContent") String postContent,
                                           @RequestParam(value = "file1", required = false) MultipartFile file1) throws Exception {
        PostRequest dto = new PostRequest();
        dto.setPostTitle(postTitle);
        dto.setPostContent(postContent);

        Post savedPost = postService.save(dto, file1);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedPost);
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
                                           @RequestParam(name = "file1", required = false) MultipartFile file1) throws Exception {

        Post post = postService.findById(postNo);

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