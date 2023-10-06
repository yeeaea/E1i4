package org.online.lms.video.controller;

import org.online.lms.video.dto.VideoInfoRequest;
import org.online.lms.video.service.VideoInfoService;
import org.online.lms.video.domain.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminVideoApiController {

    private final VideoInfoService videoInfoService;

    @Autowired
    public AdminVideoApiController(VideoInfoService videoInfoService) {
        this.videoInfoService = videoInfoService;
    }

    // 콘텐츠 추가
    @PutMapping("/api/admin/content-edit")
    public ResponseEntity<Content> contentRegister(@RequestBody VideoInfoRequest request) {

        Content contentRegister = videoInfoService.contentRegister(request);

        return ResponseEntity.ok(contentRegister);
    }

}
