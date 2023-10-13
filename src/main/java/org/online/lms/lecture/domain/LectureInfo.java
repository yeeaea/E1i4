package org.online.lms.lecture.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;


@Table(name="lecture_info")
@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureInfo { // 개설 강의 정보 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_no", updatable = false)
    private Long lectureNo; // 강의 번호

    @Column(name = "lecture_year", nullable = false)
    private String lectureYear; // 강의년도

    @Column(name = "lecture_title", nullable = false)
    private String lectureTitle; // 강의명

    @Column(name = "lecture_desc")
    private String lectureDesc; // 강의 설명

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "lecture_start_at", nullable = false)
    private LocalDate lectureStartAt; // 강의 시작일

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "lecture_end_at", nullable = false)
    private LocalDate lectureEndAt; // 강의 종료일

    @Column(name = "lecture_course", nullable = false)
    private String lectureCourse; // 강의 과정

    @Column(name = "lecture_duration", nullable = false)
    private int lectureDuration; // 총 주차 수
    @Builder
    public LectureInfo(String lectureYear,
                       String lectureTitle,
                       String lectureDesc,
                       LocalDate lectureStartAt,
                       LocalDate lectureEndAt,
                       String lectureCourse,
                       int lectureDuration) {
        this.lectureYear = lectureYear;
        this.lectureTitle = lectureTitle;
        this.lectureDesc = lectureDesc;
        this.lectureStartAt = lectureStartAt;
        this.lectureEndAt = lectureEndAt;
        this.lectureCourse = lectureCourse;
        this.lectureDuration = lectureDuration;
    }

    // 수정 메소드
    public void update(String lectureYear,
                       String lectureTitle,
                       String lectureDesc,
                       LocalDate lectureStartAt,
                       LocalDate lectureEndAt,
                       String lectureCourse,
                       int lectureDuration) {
        this.lectureYear = lectureYear;
        this.lectureTitle = lectureTitle;
        this.lectureDesc = lectureDesc;
        this.lectureStartAt = lectureStartAt;
        this.lectureEndAt = lectureEndAt;
        this.lectureCourse = lectureCourse;
        this.lectureDuration = lectureDuration;
    }
}
