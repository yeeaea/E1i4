package org.online.lms.video.controller;

import org.online.lms.video.dto.VideoInfoRequest;
import org.online.lms.video.service.VideoInfoService;
import org.online.lms.video.domain.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class VideoApiController {

    private final VideoInfoService videoInfoService;

    @Autowired
    public VideoApiController(VideoInfoService videoInfoService) {
        this.videoInfoService = videoInfoService;
    }

    // 콘텐츠 등록 + 수정
    @PutMapping("/content-edit")
    public ResponseEntity<Content> contentRegister(@RequestBody VideoInfoRequest request) {

        Content contentRegister = videoInfoService.contentRegister(request);

        return ResponseEntity.ok(contentRegister);
    }

    // 콘텐츠 삭제
    @DeleteMapping("/content/{contentNo}")
    public ResponseEntity<Void> deleteContent(@PathVariable Long contentNo) {
        videoInfoService.delete(contentNo);

        return ResponseEntity.ok().build();
    }



}
