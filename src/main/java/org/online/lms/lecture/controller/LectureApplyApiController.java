package org.online.lms.lecture.controller;

import org.online.lms.lecture.dto.LectureApplyDto;
import org.online.lms.lecture.service.LectureApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/lms/api/lectures")
public class LectureApplyApiController {

    private final LectureApplyService lectureApplyService;

    @Autowired
    public LectureApplyApiController(LectureApplyService lectureApplyService) {
        this.lectureApplyService = lectureApplyService;
    }


    // 수강신청
    @PostMapping("/apply-for-lecture")
    public ResponseEntity<Object> applyForLecture(@RequestBody LectureApplyDto lectureApplyDto) {
        boolean success = lectureApplyService.applyForLecture(lectureApplyDto);

        if (success) {
            return ResponseEntity.ok(Map.of("success", true));
        } else {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "강의 신청에 실패했습니다."));
        }
    }

    // 중복 수강신청 확인
    @GetMapping("/check-duplicate-apply")
    public ResponseEntity<Object> checkDuplicateApply(@RequestParam Long lectureNo,
                                                      @RequestParam Long memberNo) {
        boolean isDuplicate = lectureApplyService.isDuplicate(lectureNo, memberNo);
        Map<String, Object> response = new HashMap<>();
        response.put("isDuplicate", isDuplicate);
        return ResponseEntity.ok(response);
    }

    // 수료여부 확인
    @GetMapping("/completion-status")
    public ResponseEntity<String> getCompletionStatus(@RequestParam Long lectureNo, @RequestParam Long memberNo) {
        boolean isCompleted = lectureApplyService.isLectureCompleted(lectureNo, memberNo);

        if (isCompleted) {
            return ResponseEntity.ok("{\"completionStatus\": \"Y\"}");
        } else {
            return ResponseEntity.ok("{\"completionStatus\": \"N\"}");
        }
    }

}
