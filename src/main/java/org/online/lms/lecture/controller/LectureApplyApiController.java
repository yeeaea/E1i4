package org.online.lms.lecture.controller;

import org.online.lms.lecture.dto.LectureApplyDto;
import org.online.lms.lecture.service.LectureApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/lms/api/lectures")
public class LectureApplyApiController {

    private final LectureApplyService lectureApplyService;

    @Autowired
    public LectureApplyApiController(LectureApplyService lectureApplyService) {
        this.lectureApplyService = lectureApplyService;
    }

    @PostMapping("/apply-for-lecture")
    public ResponseEntity<Object> applyForLecture(@RequestBody LectureApplyDto lectureApplyDto) {
        boolean success = lectureApplyService.applyForLecture(lectureApplyDto);

        if (success) {
            return ResponseEntity.ok(Map.of("success", true));
        } else {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "강의 신청에 실패했습니다."));
        }
    }

}
