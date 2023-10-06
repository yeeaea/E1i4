package org.online.lms.lecture.controller;

import org.online.lms.lecture.domain.LectureInfo;
import org.online.lms.lecture.dto.AddLectureInfoRequest;
import org.online.lms.lecture.service.LectureInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // 관리자 : 강의 추가
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

    // 관리자 : 개설 강의 목록에서 강의 클릭했을 때, 해당 강의 정보가 input창에 기본값으로 들어가는기능
    @GetMapping("/{lectureNo}")
    public ResponseEntity<LectureInfo> getLectureInfo(@PathVariable Long lectureNo) {
        // lectureNo를 사용하여 강의 정보를 데이터베이스에서 가져옴 (예: JPA 또는 JDBC 사용)
        LectureInfo lectureInfo = lectureInfoService.findById(lectureNo);
        if (lectureInfo != null) {
            return ResponseEntity.ok(lectureInfo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
