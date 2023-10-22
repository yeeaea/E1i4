package org.online.lms.video.controller;

import lombok.RequiredArgsConstructor;
import org.online.lms.video.domain.Content;
import org.online.lms.video.domain.ProgressInfo;
import org.online.lms.video.dto.AddProgressInfoRequest;
import org.online.lms.video.dto.UpdateProgressInfoRequest;
import org.online.lms.video.service.ProgressInfoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/api/progress")
public class ProgressInfoApiController {

    private final ProgressInfoService progressInfoService;

    // 차시 정보 추가
    @PostMapping("/add")
    public ResponseEntity<ProgressInfo> addProgress(@RequestBody AddProgressInfoRequest request) {
        ProgressInfo savedProgressInfo = progressInfoService.addProgress(request);
        return ResponseEntity.ok(savedProgressInfo);
    }

    // 차시 정보 수정
    @PostMapping("/update/{nthNo}")
    public ResponseEntity<ProgressInfo> updateProgress(@PathVariable long nthNo, @RequestBody UpdateProgressInfoRequest request) {
        ProgressInfo updatedProgress = progressInfoService.updateProgress(nthNo, request);

        if (updatedProgress != null) {
            return ResponseEntity.ok(updatedProgress);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 차시 정보 삭제
    @PostMapping("/delete/{nthNo}")
    public ResponseEntity<String> deleteProgress(@PathVariable long nthNo) {
        progressInfoService.deleteProgress(nthNo);

        try {
            return ResponseEntity.ok("삭제 완료");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("삭제 오류 : " + e.getMessage());
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Page<Content>> getProgressData(
            @RequestParam("page") int page,
            @RequestParam("pageSize") int pageSize) {

        // 페이지 및 페이지 크기로 Pageable 객체 생성
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "contentNo"));

        // 페이지별로 데이터를 조회
        Page<Content> pageOfContent = progressInfoService.getAllContent(pageable);

        // 조회된 페이지 데이터를 클라이언트에 반환
        return ResponseEntity.ok(pageOfContent);
    }
}
