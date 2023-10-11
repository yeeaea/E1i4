package org.online.lms.video.controller;


import org.online.lms.video.domain.Content;
import org.online.lms.video.dto.VideoInfoRequest;
import org.online.lms.video.dto.VideoInfoViewResponse;
import org.online.lms.video.service.VideoInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class VideoViewController {

    private final VideoInfoService videoInfoService;
    @Autowired
    public VideoViewController(VideoInfoService videoInfoService) {
        this.videoInfoService = videoInfoService;
    }


    @GetMapping("/content")
    public String lectureInfoList(Model model) {

        // 콘텐츠 리스트
        List<VideoInfoViewResponse> contentInfo = videoInfoService.findAll().stream()
                .map(VideoInfoViewResponse::new)
                .toList();
        model.addAttribute("contentInfo", contentInfo);

        return "/page/video/videoList"; 
    }

    @GetMapping("/content-edit")
    public String contentRegister(Model model) {

        // 콘텐츠 리스트 + 등록 + 수정
        List<Content> contentList = videoInfoService.findAll();
        model.addAttribute("contentList", contentList);
        model.addAttribute("videoInfoRequest", new VideoInfoRequest());

        return "/page/video/videoUpdate";
    }

    /////// 추후에 viewController로 이동(Security) - 차시테이블 사용
    @GetMapping("/lms/online/progress-info-list")
    public String ytbList() {
        return "/page/video/ytbContent";
    }
}
