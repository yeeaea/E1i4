package org.online.lms.boards.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.online.lms.boards.domain.Comment;
import org.online.lms.boards.domain.FileUpload;
import org.online.lms.boards.domain.Post;
import org.online.lms.boards.dto.CommentListViewResponse;
import org.online.lms.boards.dto.PostViewResponse;
import org.online.lms.boards.service.CommentService;
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

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Controller
public class NoticeViewController {

    private final PostService postService;
    private final CommentService commentService;


    // 게시글 목록 조회 (멤버)
    @GetMapping("/lms/notice")
    public String getNoticeList(@PageableDefault(size = 10) Pageable pageable,
                                Model model,
                                @RequestParam(required = false) String keyword,
                                @RequestParam(required = false) String sortBy) {
        Page<Post> boards;

        if (keyword == null) {
            if ("most-visited".equals(sortBy)) {
                // 조회순 정렬
                pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                        Sort.by("postView").descending());
                boards = postService.findByBoardNoOrderByPostViewDesc(2L, pageable); // boardNo가 1인 게시글만 가져오도록 수정

            } else {
                // 최신순 정렬
                pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                        Sort.by("postRtm").descending());
                boards = postService.findByBoardNo(2L, pageable);
            }

        } else {
            boards = postService.findByBoardNoAndPostTitleContaining(2L, keyword, pageable);

        }
        if (boards.isEmpty()) {
            model.addAttribute("noResults", true); // 검색 결과가 없다는 플래그 추가
        }

        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boards.getPageable().getPageNumber() + 4, boards.getTotalPages());

        model.addAttribute("notice", boards);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("sortBy", sortBy);

        return "page/boards/memberNoticeList.html";
    }


    // 게시글 1개 조회 (멤버)
    @GetMapping("/lms/notice/{postNo}")
    public String getNotice(@PathVariable Long postNo,
                            Model model,
                            HttpSession session,
                            HttpServletRequest request) {

        // 현재 로그인한 사용자 정보 가져오기
        Principal principal = request.getUserPrincipal();

        String currentLoginId = null; // 초기화

        if (principal != null) {
            currentLoginId = principal.getName();
        } else {
            // 사용자가 인증되지 않은 경우에 처리
            currentLoginId = "anonymous";
        }

        // 글 정보 가져오기
        Post notice = postService.getQues(postNo, session);

        // 글 작성자 정보 가져오기
        String loginId = notice.getLoginId();


        FileUpload file = notice.getFile();

        PostViewResponse noticeResponse = new PostViewResponse(notice, file);

        // 댓글 목록을 가져오기
        List<Comment> comments =
                commentService.getCommentsByPostNo(postNo);
        List<CommentListViewResponse> commentResponses =
                comments.stream()
                        .map(CommentListViewResponse::new)
                        .collect(Collectors.toList());

        model.addAttribute("notice", noticeResponse);
        model.addAttribute("comments", commentResponses);
        model.addAttribute("currentLoginId", currentLoginId);
        model.addAttribute("loginId", loginId);
        return "page/boards/memberNotice.html";
    }


    // 게시물 작성 (관리자)
    @GetMapping("/admin/new-notice")
    public String newNotice(@RequestParam(required = false) Long postNo,
                            Model model) {

        if (postNo == null) {
            model.addAttribute("notice", new PostViewResponse());
        } else {
            Post post = postService.findById(postNo);
            FileUpload file = post != null ? post.getFile() : null; // 게시물과 연결된 파일 가져오기 (File 객체로 가정)

            // File 객체를 사용하여 PostViewResponse 객체 생성
            PostViewResponse noticeResponse = new PostViewResponse(post, file);

            model.addAttribute("notice", noticeResponse);
        }

        return "page/boards/newNotice.html";
    }

    // 게시글 목록 조회 (관리자)
    @GetMapping("/admin/notice")
    public String getNoticeList2(@PageableDefault(size = 10) Pageable pageable,
                                 Model model,
                                 @RequestParam(required = false) String keyword,
                                 @RequestParam(required = false) String sortBy) {
        Page<Post> boards;

        if (keyword == null) {
            if ("most-visited".equals(sortBy)) {
                // 조회순 정렬
                pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                        Sort.by("postView").descending());
                boards = postService.findByBoardNoOrderByPostViewDesc(2L, pageable); // boardNo가 1인 게시글만 가져오도록 수정

            } else {
                // 최신순 정렬
                pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                        Sort.by("postRtm").descending());
                boards = postService.findByBoardNo(2L, pageable);
            }

        } else {
            boards = postService.findByBoardNoAndPostTitleContaining(2L, keyword, pageable);

        }
        if (boards.isEmpty()) {
            model.addAttribute("noResults", true); // 검색 결과가 없다는 플래그 추가
        }

        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boards.getPageable().getPageNumber() + 4, boards.getTotalPages());

        model.addAttribute("notice", boards);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("sortBy", sortBy);

        return "page/boards/adminNoticeList.html";
    }


    // 게시글 1개 조회 (관리자)
    @GetMapping("/admin/notice/{postNo}")
    public String getNotice2(@PathVariable Long postNo,
                             Model model,
                             HttpSession session,
                             HttpServletRequest request) {

        // 현재 로그인한 사용자 정보 가져오기
        Principal principal = request.getUserPrincipal();

        String currentLoginId = null; // 초기화

        if (principal != null) {
            currentLoginId = principal.getName();
        } else {
            // 사용자가 인증되지 않은 경우에 처리
            currentLoginId = "anonymous";
        }

        // 글 정보 가져오기
        Post notice = postService.getQues(postNo, session);

        // 글 작성자 정보 가져오기
        String loginId = notice.getLoginId();


        FileUpload file = notice.getFile();

        PostViewResponse noticeResponse = new PostViewResponse(notice, file);

        // 댓글 목록을 가져오기
        List<Comment> comments =
                commentService.getCommentsByPostNo(postNo);
        List<CommentListViewResponse> commentResponses =
                comments.stream()
                        .map(CommentListViewResponse::new)
                        .collect(Collectors.toList());

        model.addAttribute("notice", noticeResponse);
        model.addAttribute("comments", commentResponses);
        model.addAttribute("currentLoginId", currentLoginId);
        model.addAttribute("loginId", loginId);
        return "page/boards/adminNotice.html";
    }

}
