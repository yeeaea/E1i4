package org.online.lms.lecture.controller;

import org.online.lms.lecture.dto.LectureApplyDto;
import org.online.lms.lecture.service.LectureApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lms/api/lectures")
public class LectureApplyApiController {

    private final LectureApplyService lectureApplyService;

    @Autowired
    public LectureApplyApiController(LectureApplyService lectureApplyService) {
        this.lectureApplyService = lectureApplyService;
    }

    @PostMapping("/apply-for-lecture")
    public ResponseEntity<?> applyForLecture(@RequestBody LectureApplyDto lectureApplyDto) {
        try {
            // 서비스 메서드 호출 결과를 사용하여 성공 또는 실패 여부를 판단
            lectureApplyService.applyForLecture(lectureApplyDto);

            // 성공한 경우
            return ResponseEntity.ok().body("{\"success\": true}");
        } catch (Exception e) {
            // 실패한 경우
            return ResponseEntity.ok().body("{\"success\": false}");
        }
    }
}
