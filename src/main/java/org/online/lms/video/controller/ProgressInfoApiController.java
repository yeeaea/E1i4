package org.online.lms.video.controller;

import lombok.RequiredArgsConstructor;
import org.online.lms.lecture.domain.LectureInfo;
import org.online.lms.video.domain.Content;
import org.online.lms.video.domain.ProgressInfo;
import org.online.lms.video.dto.AddProgressInfoRequest;
import org.online.lms.video.dto.UpdateProgressInfoRequest;
import org.online.lms.video.repository.ProgressInfoRepository;
import org.online.lms.video.service.ProgressInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/api/progress")
public class ProgressInfoApiController {

    private final ProgressInfoRepository progressInfoRepository;
    private final ProgressInfoService progressInfoService;
    private final List<Long> usedNthNo = new ArrayList<>();

    // 차시 정보 추가
    @PostMapping("/add")
    public ResponseEntity<ProgressInfo> addProgress(@RequestBody AddProgressInfoRequest request) {
        ProgressInfo progressInfo = progressInfoService.addProgress(request);
        return ResponseEntity.ok(progressInfo);
    }

    // 차시 번호로 차시 정보 찾아오기
    @GetMapping("/getByNthNo/{nthNo}")
    public ResponseEntity<ProgressInfo> getProgressByNthNo(@PathVariable long nthNo) {
        Optional<ProgressInfo> progressInfoOptional = progressInfoRepository.findById(nthNo);

        return progressInfoOptional
                .map(progressInfo -> ResponseEntity.ok().body(progressInfo))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // 차시 정보 수정
    @PutMapping("/update/{nthNo}")
    public ResponseEntity<ProgressInfo> updateProgress(@PathVariable long nthNo, @RequestParam int nthDuration, @RequestBody UpdateProgressInfoRequest request) {
        ProgressInfo updatedProgress = progressInfoService.updateProgress(nthNo, nthDuration, request);

        if (updatedProgress != null) {
            return ResponseEntity.ok(updatedProgress);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 차시 정보 삭제
    @DeleteMapping("/delete/{nthNo}")
    public ResponseEntity<String> deleteProgress(@PathVariable long nthNo) {
        progressInfoService.deleteProgress(nthNo);

        try {
            return ResponseEntity.ok("삭제 완료");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("삭제 오류 : " + e.getMessage());
        }
    }

    // 차시 번호 중복 검사 (수정중)
    @PostMapping("/check-nthNo/{nthNo}")
    public String checkDuplicateNthNo(@PathVariable long nthNo) {
        if (usedNthNo.contains(nthNo)) {
            return "차시 관리 번호 " + nthNo + "는 이미 사용 중입니다. 다른 번호를 선택해주세요.";
        } else {
            usedNthNo.add(nthNo);
            return "차시 관리 번호 " + nthNo + "는 사용 가능합니다.";
        }
    }
}
