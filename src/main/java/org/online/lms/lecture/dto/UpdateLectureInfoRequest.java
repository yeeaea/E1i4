package org.online.lms.lecture.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가
@Getter
@Setter
public class UpdateLectureInfoRequest {
    private String lectureYear; // 강의년도
    private String lectureTitle; // 강의명
    private String lectureDesc; // 강의 설명
    private Timestamp lectureStartAt; // 강의 시작일
    private Timestamp lectureEndAt; // 강의 종료일
    private String lectureCourse; // 강의 과정
    private int lectureDuration; // 총 주차 수
}
