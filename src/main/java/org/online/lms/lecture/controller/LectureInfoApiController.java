package org.online.lms.lecture.controller;

import org.online.lms.lecture.domain.LectureInfo;
import org.online.lms.lecture.dto.AddLectureInfoRequest;
import org.online.lms.lecture.dto.UpdateLectureInfoRequest;
import org.online.lms.lecture.service.LectureInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    // 관리자 : 강의 수정
    @PutMapping("/update/{lectureNo}")
    public ResponseEntity<LectureInfo> updateLecture(@PathVariable Long lectureNo, @RequestBody UpdateLectureInfoRequest request) {
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

        // UpdateLectureInfoRequest 엔티티에 설정
        request.setLectureStartAt(startTimestamp);
        request.setLectureEndAt(endTimestamp);

        LectureInfo updatedLecture = lectureInfoService.updateLecture(lectureNo, request);

        if (updatedLecture != null) {
            return ResponseEntity.ok(updatedLecture);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 관리자 : 강의 삭제
    @PostMapping("/delete")
    public ResponseEntity<String> deleteLectures(@RequestBody List<Long> lectureNos) {
        // lectureNos 목록에 포함된 강의를 삭제합니다.
        boolean success = lectureInfoService.deleteLectures(lectureNos);

        if (success) {
            return new ResponseEntity<>("강의가 삭제되었습니다.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("강의 삭제 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}



