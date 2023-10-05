package org.online.lms.survey.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.online.lms.lecture.domain.LectureApply;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@Table(name = "survey")
@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_no")
    private long surveyNo; // 강의 평가 실시 번호

    // 데이터베이스에 대한 추가 및 갱신에 영향을 주지 않음 = false
    // 데이터베이스에 대한 추가 및 갱신에 영향을 줌 = true
    @OneToOne
    @JoinColumn(name = "lecture_no", referencedColumnName = "lecture_no"
            /*insertable = false, updatable = false*/)
    private LectureApply lectureNo; // 강의 번호 (외래키 연결)

    @OneToOne
    @JoinColumn(name = "member_no", referencedColumnName = "member_no"
            /*insertable = false, updatable = false*/)
    private LectureApply memberNo; // 회원 번호 (외래키 연결)

    @Column(name = "survey_at", nullable = false)
    private Timestamp surveyAt;

    @Builder
    public Survey(long surveyNo,
                  LectureApply lectureNo,
                  LectureApply memberNo,
                  Timestamp surveyAt) {
        this.surveyNo = surveyNo;
        this.lectureNo = lectureNo;
        this.memberNo = memberNo;
        this.surveyAt = surveyAt;
    }
}
