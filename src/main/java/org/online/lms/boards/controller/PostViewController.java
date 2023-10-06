package org.online.lms.boards.controller;

import lombok.RequiredArgsConstructor;
import org.online.lms.boards.domain.Post;
import org.online.lms.boards.dto.PostViewResponse;
import org.online.lms.boards.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;


@RequiredArgsConstructor
@Controller
public class PostViewController {
    private final PostService postService;

    // 게시글 목록 조회
    @GetMapping("/lms/questions")
    public String getQuestions(@PageableDefault(size = 5) Pageable pageable,
                               Model model,
                               @RequestParam(required = false) String keyword,
                               @RequestParam(required = false) String sortBy) {
        Page<Post> boards;

        if (keyword == null) {
            if ("most-visited".equals(sortBy)) {
                // 조회순 정렬
                pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                        Sort.by("postView").descending());
                boards = postService.findAllByOrderByPostViewDesc(pageable);

            } else {
                // 최신순 정렬
                pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                        Sort.by("postRtm").descending());
                boards = postService.findAll(pageable);
            }

        } else {
            boards = postService.boardSearchList(keyword, pageable);
        }
        if (boards.isEmpty()) {
            model.addAttribute("noResults", true); // 검색 결과가 없다는 플래그 추가
        }

        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boards.getPageable().getPageNumber() + 4, boards.getTotalPages());

        model.addAttribute("questions", boards);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("sortBy", sortBy);

        return "boards/questionList.html";
    }

    // 게시글 1개 조회
    @GetMapping("/lms/questions/{postNo}")
    public String getQuestion(@PathVariable Long postNo, HttpSession session, Model model) {

        // 글 정보 가져오기
        Post question = postService.getQues(postNo, session);

        model.addAttribute("question", question);


        return "boards/question.html";
    }

    // 게시물 작성
    @GetMapping("/lms/new-question")
    public String newQuestion(@RequestParam(required = false) Long postNo, Model model) {
        if (postNo == null) {
            model.addAttribute("question", new PostViewResponse());
        } else {
            Post post = postService.findById(postNo);
            model.addAttribute("question", new PostViewResponse(post));
        }

        return "boards/newQuestion.html";
    }


}
