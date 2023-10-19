package org.online.lms.lecture.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가
@Getter
@Setter
public class MyLectureInfoDto {
    private String lectureCourse;
    private String lectureTitle;
    private LocalDate lectureStartAt;
    private LocalDate lectureEndAt;
    private boolean completionYn;

}
