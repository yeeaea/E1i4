package org.online.lms.member.lecture.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

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

    @Column(name = "lecture_start_at", nullable = false)
    private Timestamp lectureStartAt; // 강의 시작일

    @Column(name = "lecture_end_at", nullable = false)
    private Timestamp lectureEndAt; // 강의 종료일

    @Column(name = "lecture_course", nullable = false)
    private String lectureCourse; // 강의 과정

    @Builder
    public LectureInfo(String lectureYear,
                       String lectureTitle,
                       String lectureDesc,
                       Timestamp lectureStartAt,
                       Timestamp lectureEndAt,
                       String lectureCourse) {
        this.lectureYear = lectureYear;
        this.lectureTitle = lectureTitle;
        this.lectureDesc = lectureDesc;
        this.lectureStartAt = lectureStartAt;
        this.lectureEndAt = lectureEndAt;
        this.lectureCourse = lectureCourse;
    }
}
