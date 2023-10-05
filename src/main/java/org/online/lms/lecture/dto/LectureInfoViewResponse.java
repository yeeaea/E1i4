package org.online.lms.lecture.dto;

import lombok.Getter;
import lombok.Setter;
import org.online.lms.lecture.domain.LectureInfo;

import java.sql.Timestamp;


@Getter
@Setter
public class LectureInfoViewResponse {

    private Long lectureNo; // 강의 번호
    private String lectureYear; // 강의년도
    private String lectureTitle; // 강의명
    private String lectureDesc; // 강의 설명
    private Timestamp lectureStartAt; // 강의 시작일
    private Timestamp lectureEndAt; // 강의 종료일
    private String lectureCourse; // 강의 과정

    public LectureInfoViewResponse(LectureInfo lectureInfo) {
        this.lectureNo = lectureInfo.getLectureNo();
        this.lectureYear = lectureInfo.getLectureYear();
        this.lectureTitle = lectureInfo.getLectureTitle();
        this.lectureDesc = lectureInfo.getLectureDesc();
        this.lectureStartAt = lectureInfo.getLectureStartAt();
        this.lectureEndAt = lectureInfo.getLectureEndAt();
        this.lectureCourse = lectureInfo.getLectureCourse();
    }
}
