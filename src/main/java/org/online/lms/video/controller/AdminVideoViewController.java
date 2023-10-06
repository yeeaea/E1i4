package org.online.lms.video.controller;


import org.online.lms.video.domain.Content;
import org.online.lms.video.dto.VideoInfoRequest;
import org.online.lms.video.service.VideoInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminVideoViewController {

    private final VideoInfoService videoInfoService;
    @Autowired
    public AdminVideoViewController(VideoInfoService videoInfoService) {
        this.videoInfoService = videoInfoService;
    }


    @GetMapping("/admin/content-edit")
    public String contentRegister(Model model) {

        // 콘텐츠 등록 + 리스트!
        List<Content> contentList = videoInfoService.findAll();
        model.addAttribute("contentList", contentList);
        model.addAttribute("videoInfoRequest", new VideoInfoRequest());

        return "/video/videoRegister";
    }
}
