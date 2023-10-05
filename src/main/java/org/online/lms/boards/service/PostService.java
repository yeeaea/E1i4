package org.online.lms.boards.service;

import lombok.RequiredArgsConstructor;
import org.online.lms.boards.repository.PostRepository;
import org.online.lms.boards.domain.Post;
import org.online.lms.boards.dto.PostRequest;
import org.online.lms.boards.dto.UpdatePostRequest;
import org.springframework.stereotype.Service;

import java.util.List;


import jakarta.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    // 글 등록 (첨부파일 추가예정)
    public Post save(PostRequest dto) {
        return postRepository.save(dto.toEntity());
    }

    // 글 목록 조회
    public List<Post> findAll() {
        return postRepository.findAll();
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
    public Post update(Long postNo, UpdatePostRequest request){
        Post post = postRepository.findById(postNo)
                .orElseThrow(() ->
                        new IllegalArgumentException(postNo + "번 글이 존재하지 않습니다."));

        post.update(request.getPostTitle(), request.getPostContent());

        return post;
    }


}
