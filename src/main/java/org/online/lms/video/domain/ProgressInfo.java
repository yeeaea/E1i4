package org.online.lms.video.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.online.lms.lecture.domain.LectureInfo;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table(name="progress_info")
@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProgressInfo { // 강의 차시 정보 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nth_no", updatable = false)
    private long nthNo; // 차시 관리 번호

    // 데이터베이스에 대한 추가 및 갱신에 영향을 주지 않음 = false
    // 데이터베이스에 대한 추가 및 갱신에 영향을 줌 = true
    @OneToOne
    @JoinColumn(name = "lecture_no", referencedColumnName = "lecture_no"
                /*insertable = false, updatable = false*/)
    private LectureInfo lectureNo; // 강의 번호 (외래키 연결)

    @Column(name = "nth_name")
    private String nthName; // 차시명

    @Column(name = "run_tm")
    private int runTm; // 차시학습시간

    @OneToOne
    @JoinColumn(name = "content_mgmt_no", referencedColumnName = "content_mgmt_no"
                /*insertable = false, updatable = false*/)
    private Content contentNo; // 콘텐츠 관리 번호 (외래키 연결)

    @Builder
    public ProgressInfo(long nthNo,
                        LectureInfo lectureNo,
                        String nthName,
                        int runTm,
                        Content contentNo) {
        this.nthNo = nthNo;
        this.lectureNo = lectureNo;
        this.nthName = nthName;
        this.runTm = runTm;
        this.contentNo = contentNo;
    }
}
