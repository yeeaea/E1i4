package org.online.lms.admin.video.controller;

import org.online.lms.admin.video.domain.Content;
import org.online.lms.admin.video.dto.VideoInfoRequest;
import org.online.lms.admin.video.service.VideoInfoService;
import org.online.lms.lecture.domain.LectureInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
