package org.online.lms.video.controller;

import lombok.RequiredArgsConstructor;
import org.online.lms.lecture.domain.LectureInfo;
import org.online.lms.video.domain.Content;
import org.online.lms.video.domain.ProgressInfo;
import org.online.lms.video.dto.AddProgressInfoRequest;
import org.online.lms.video.dto.UpdateProgressInfoRequest;
import org.online.lms.video.service.ProgressInfoService;
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
        ProgressInfo addedProgress = progressInfoService.addProgress(request);
        return ResponseEntity.ok(addedProgress);
    }

    // 차시 정보 수정
    @PutMapping("/update/{nthNo}")
    public ResponseEntity<ProgressInfo> updateProgress(@PathVariable long nthNo, @RequestBody UpdateProgressInfoRequest request) {
        ProgressInfo updatedProgress = progressInfoService.updateProgress(nthNo, request);

        if(updatedProgress != null) {
            return ResponseEntity.ok(updatedProgress);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 차시 정보 삭제
    @DeleteMapping("/delete/{nthNo}")
    public ResponseEntity<String> deleteProgress(@PathVariable long nthNo) {
        try {
            // Call the service to delete progress
            progressInfoService.deleteProgress(nthNo);
            return ResponseEntity.ok("삭제 완료");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("삭제 오류 : " + e.getMessage());
        }
    }
}
