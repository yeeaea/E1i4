package org.online.lms.video.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.online.lms.lecture.domain.LectureApply;
import org.online.lms.lecture.domain.LectureInfo;
import org.online.lms.security.domain.Members;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table(name="progress")
@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Progress { // 강의 학습자별 차시 진도 테이블

    // 데이터베이스에 대한 추가 및 갱신에 영향을 주지 않음 = false
    // 데이터베이스에 대한 추가 및 갱신에 영향을 줌 = true
    @Id
    @OneToOne
    @JoinColumn(name = "nth_no", referencedColumnName = "nth_no"
            /*insertable = false, updatable = false*/)
    private ProgressInfo nthNo; // 차시 관리 번호 (외래키 연결)

    @OneToOne
    @JoinColumn(name = "lecture_no", referencedColumnName = "lecture_no"
            /*insertable = false, updatable = false*/)
    private LectureApply lectureNo; // 강의 번호 (외래키 연결)

    @OneToOne
    @JoinColumn(name = "member_no", referencedColumnName = "member_no"
            /*insertable = false, updatable = false*/)
    private LectureApply memberNo; // 회원 번호 (외래키 연결)

    @Column(name = "start_tm")
    private String startTm; // 접속 시작 시간

    @Column(name = "end_tm")
    private String endTm; // 접속 종료 시간

    @Column(name = "final_tm", nullable = false)
    private String finalTm; // 강의 최종 재생 위치

    @Column(name = "max_tm")
    private String maxTm; // 강의 수강 종료 위치

    @Column(name = "prog_rt")
    private Integer progRt; // 차시 진도율

    @Builder
    public Progress(ProgressInfo nthNo,
                    LectureApply lectureNo,
                    LectureApply memberNo,
                    String startTm,
                    String endTm,
                    String finalTm,
                    String maxTm,
                    Integer progRt) {
        this.nthNo = nthNo;
        this.lectureNo = lectureNo;
        this.memberNo = memberNo;
        this.startTm = startTm;
        this.endTm = endTm;
        this.finalTm = finalTm;
        this.maxTm = maxTm;
        this.progRt = progRt;
    }
}