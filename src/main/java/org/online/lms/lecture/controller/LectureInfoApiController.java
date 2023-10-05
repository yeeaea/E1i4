package org.online.lms.lecture.controller;

import org.online.lms.lecture.domain.LectureInfo;
import org.online.lms.lecture.dto.AddLectureInfoRequest;
import org.online.lms.lecture.service.LectureInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/admin/api/lectures")
public class LectureInfoApiController {

    private final LectureInfoService lectureInfoService;

    @Autowired
    public LectureInfoApiController(LectureInfoService lectureInfoService) {
        this.lectureInfoService = lectureInfoService;
    }

    @PostMapping("/add")
    public ResponseEntity<LectureInfo> addLecture(@RequestBody AddLectureInfoRequest request) {
        // 강의 시작일과 종료일을 LocalDateTime 형식으로 파싱
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        String lectureStartAtString = String.valueOf(request.getLectureStartAt());
        String lectureEndAtString = String.valueOf(request.getLectureEndAt());

        // LocalDateTime 형식으로 파싱
        LocalDateTime startDateTime = LocalDateTime.parse(lectureStartAtString, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(lectureEndAtString, formatter);

        // LocalDateTime을 Timestamp로 변환
        Timestamp startTimestamp = Timestamp.valueOf(startDateTime);
        Timestamp endTimestamp = Timestamp.valueOf(endDateTime);

        // AddLectureInfoRequest 엔티티에 설정
        request.setLectureStartAt(startTimestamp);
        request.setLectureEndAt(endTimestamp);

        LectureInfo addedLecture = lectureInfoService.addLecture(request);

        return ResponseEntity.ok(addedLecture);
    }
}
