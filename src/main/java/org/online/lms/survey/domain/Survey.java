package org.online.lms.survey.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private Long surveyNo; // 강의 평가 실시 번호

    @Column(name = "lecture_no")
    private Long lectureNo; // 강의 번호 (외래키 연결하지 않고, 값만 받아와서 입력)

    @Column(name = "member_no")
    private Long memberNo; // 회원 번호 (외래키 연결하지 않고, 값만 받아와서 입력)

    @Column(name = "survey_at", nullable = false)
    private Timestamp surveyAt;

    @Builder
    public Survey(Long surveyNo,
                  Long lectureNo,
                  Long memberNo,
                  Timestamp surveyAt) {
        this.surveyNo = surveyNo;
        this.lectureNo = lectureNo;
        this.memberNo = memberNo;
        this.surveyAt = surveyAt;
    }
}
