package org.online.lms.lecture.controller;

import org.online.lms.lecture.domain.LectureInfo;
import org.online.lms.lecture.dto.AddLectureInfoRequest;
import org.online.lms.lecture.dto.UpdateLectureInfoRequest;
import org.online.lms.lecture.service.LectureInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

        LectureInfo addedLecture = lectureInfoService.addLecture(request);

        return ResponseEntity.ok(addedLecture);
    }

    // 관리자 : 강의 수정
    @PutMapping("/update/{lectureNo}")
    public ResponseEntity<LectureInfo> updateLecture(@PathVariable Long lectureNo,
                                                     @RequestBody UpdateLectureInfoRequest request) {

        // lectureNo를 사용하여 강의 정보 데이터베이스에서 가져오기
        LectureInfo updateLecture = lectureInfoService.updateLectures(lectureNo, request);

        return ResponseEntity.ok().body(updateLecture);
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



