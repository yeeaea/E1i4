package org.online.lms.boards.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.online.lms.boards.repository.PostRepository;
import org.online.lms.boards.domain.Post;
import org.online.lms.boards.dto.PostRequest;
import org.online.lms.boards.dto.UpdatePostRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.io.File;

import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    int fileSeq = 1; // 파일 일련번호 초기화

    // 글 등록
    public Post save(PostRequest dto, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws Exception {
        String projectPath =
                System.getProperty("user.dir") + "/src/main/resources/static/files";

        // 파일1 처리
        if (file1 != null && !file1.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String fileName = uuid + "_" + file1.getOriginalFilename();
            File saveFile = new File(projectPath, fileName);
            file1.transferTo(saveFile);

            dto.setFileSeq((long) fileSeq++);
            dto.setOrgFileName(file1.getOriginalFilename());
            dto.setSaveFileName(fileName);
            dto.setFilePath("/files/" + fileName);
            dto.setFileSize(file1.getSize());
        }

        // 파일2 처리
        if (file2 != null && !file2.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String fileName = uuid + "_" + file2.getOriginalFilename();
            File saveFile = new File(projectPath, fileName);
            file2.transferTo(saveFile);

            dto.setFileSeq((long) fileSeq++);
            dto.setOrgFileName(file2.getOriginalFilename());
            dto.setSaveFileName(fileName);
            dto.setFilePath("/files/" + fileName);
            dto.setFileSize(file2.getSize());
        }

        // 파일3 처리
        if (file3 != null && !file3.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String fileName = uuid + "_" + file3.getOriginalFilename();
            File saveFile = new File(projectPath, fileName);
            file3.transferTo(saveFile);

            dto.setFileSeq((long) fileSeq++);
            dto.setOrgFileName(file3.getOriginalFilename());
            dto.setSaveFileName(fileName);
            dto.setFilePath("/files/" + fileName);
            dto.setFileSize(file3.getSize());
        }

        return postRepository.save(dto.toEntity());
    }
    // 글 목록 조회
    public Page<Post> findAll(Pageable pageable) {
        Pageable Pageable =
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                        Sort.by(Sort.Direction.DESC, "postNo"));

        return postRepository.findAll(Pageable);
    }

    // 글 하나 조회
    public Post findById(Long postNo) {
        return postRepository.findById(postNo)
                .orElseThrow(() ->
                        new IllegalArgumentException(postNo + "번 글이 존재하지 않습니다."));
    }

    // 글 삭제
    public void delete(Long postNo) {
        postRepository.deleteById(postNo);
    }


    // 글 수정 (파일 추가 예정)
    @Transactional
    public Post update(Long postNo, UpdatePostRequest request) {
        Post post = postRepository.findById(postNo)
                .orElseThrow(() ->
                        new IllegalArgumentException(postNo + "번 글이 존재하지 않습니다."));

        post.setPostRtm(LocalDateTime.now());
        post.update(request.getPostTitle(), request.getPostContent());

        return post;
    }

    // 키워드 검색
    public Page<Post> boardSearchList(String keyword, Pageable pageable) {
        return postRepository.findByPostTitleContaining(keyword, pageable);
    }

    // 세션을 통한 중복 방지 조회수 증가
    public Post getQues(long postNo, HttpSession session) {
        Optional<Post> post = postRepository.findById(postNo);
        if (post.isPresent()) {
            Post question = post.get();

            String visitedKey = "visited_question_" + postNo;

            Boolean hasVisited = (Boolean) session.getAttribute(visitedKey);

            if (hasVisited == null || !hasVisited) {
                // 사용자가 해당 게시물을 아직 조회하지 않은 경우에만 조회수 증가
                question.setPostView(question.getPostView() + 1);
                postRepository.save(question);
                // 세션에 해당 키를 저장하여 중복 조회수 증가를 방지
                session.setAttribute(visitedKey, true);
            }
            return question;
        } else {
            return null;
        }
    }

    // 조회수 정렬
    public Page<Post> findAllByOrderByPostViewDesc(Pageable pageable) {
        Pageable descendingPageable =
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                        Sort.by(Sort.Direction.DESC, "postView"));

        return postRepository.
                findAllByOrderByPostViewDesc(descendingPageable);
    }

}
