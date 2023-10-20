package org.online.lms.video.controller;


import lombok.extern.slf4j.Slf4j;
import org.online.lms.video.domain.Content;
import org.online.lms.video.domain.ProgressInfo;
import org.online.lms.video.dto.ProgressTmRequest;
import org.online.lms.video.dto.VideoInfoRequest;
import org.online.lms.video.dto.VideoInfoViewResponse;
import org.online.lms.video.service.ProgressTmService;
import org.online.lms.video.service.VideoInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin")
public class VideoViewController {

    private final VideoInfoService videoInfoService;
    @Autowired
    public VideoViewController(
            VideoInfoService videoInfoService) {
        this.videoInfoService = videoInfoService;
    }

    @GetMapping("/contentList")
    public String contentList(Model model) {
        // 콘텐츠 단순 리스트 조회

        List<VideoInfoViewResponse> contentInfo = videoInfoService.findAll().stream()
                .map(VideoInfoViewResponse::new)
                .toList();
        model.addAttribute("contentInfo", contentInfo);

        return "/page/video/videoList";
    }

    @GetMapping("/content")
    public String contentListForm(@PageableDefault(size = 10) Pageable pageable, Model model) {
        // 콘텐츠 리스트 + 등록 + 수정 + 삭제
        // 여기 템플릿만 보여주면 됨

        Page<Content> contentInfo;

        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by("contentNo").descending());
        contentInfo = videoInfoService.findAll(pageable);

        int startPage = Math.max(1, contentInfo.getPageable().getPageNumber() - 1);
        int endPage = Math.min(contentInfo.getPageable().getPageNumber() + 3, contentInfo.getTotalPages());

        model.addAttribute("contentInfo", contentInfo);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "/page/video/videoListForm";
    }

    @GetMapping("/content/save")
    public String contentListSave(Model model) {
        // 콘텐츠 저장하는 부분
        // !!! 주의 !!! 여기 uri는 프로그램 시작하고 딱 한번만 실행하기
        // uri 한번 치고 들어가서 새로고침 금지(계속 생성됨...)
        // uri통해서 들어가면 리스트에 아무것도 뜨지 않지만 데이터 저장됐습니다.
        // 다른 작업하고자 하는 부분으로 가서 작업하기~~

        List<VideoInfoViewResponse> contentInfo = videoInfoService.findAll().stream()
                .map(VideoInfoViewResponse::new)
                .toList();
        model.addAttribute("contentInfo", contentInfo);

        return "/page/video/videoListSave";
    }

    @GetMapping("/content-edit")
    public String contentRegister(Model model) {
        // 콘텐츠 리스트 + 등록 + 수정

        List<Content> contentList = videoInfoService.findAll();
        model.addAttribute("contentList", contentList);
        model.addAttribute("videoInfoRequest", new VideoInfoRequest());

        return "/page/video/videoUpdate";
    }

}