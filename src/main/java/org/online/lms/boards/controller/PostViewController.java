package org.online.lms.boards.controller;

import lombok.RequiredArgsConstructor;
import org.online.lms.boards.domain.Post;
import org.online.lms.boards.dto.PostViewResponse;
import org.online.lms.boards.dto.PostListViewResponse;
import org.online.lms.boards.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class PostViewController {
    private final PostService postService;

    // 게시글 목록 조회
    @GetMapping("/lms/questions")
    public String getQuestions(Model model) {
        List<PostListViewResponse> questions = postService.findAll()
                .stream()
                .map(PostListViewResponse::new)
                .toList();
        model.addAttribute("questions", questions);

        return "boards/questionList.html";
    }

    // 게시글 1개 조회
    @GetMapping("/lms/questions/{postNo}")
    public String getQuestion(@PathVariable Long postNo, Model model) {
        Post post = postService.findById(postNo);
        model.addAttribute("question", new PostViewResponse(post));

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
