package org.online.lms.video.controller;


import lombok.extern.slf4j.Slf4j;
import org.online.lms.video.dto.VideoInfoRequest;
import org.online.lms.video.dto.ytbDTO;
import org.online.lms.video.service.VideoInfoService;
import org.online.lms.video.domain.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
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

    // 유튜브 api에서 추출한 값 Content DB테이블의 컬럼에 저장하기
    @PostMapping("/saveYoutubeData")
    public Content saveYoutubeData(@RequestBody ytbDTO dto) {

        log.info("진입");
        log.info(dto.getVideoTitle());
        Content content = Content.contentYtb();
        content.setContentName(dto.getVideoTitle()); // YouTube 데이터의 제목을 contentName에 설정
        content.setContentDesc(dto.getVideoDescription()); // YouTube 데이터의 설명을 contentDesc에 설정
        content.setYtbUrl(dto.getVideoId()); // YouTube 데이터의 videoId를 ytbUrl에 설정

        return videoInfoService.saveContent(content);
    }



}
