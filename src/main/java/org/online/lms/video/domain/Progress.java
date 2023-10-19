package org.online.lms.video.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.online.lms.lecture.domain.LectureApply;
import org.online.lms.security.domain.Members;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table(name="progress")
@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Progress { // 강의 학습자별 차시 진도 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "progress_no")
    private Long progressNo; // 고유 번호

    // 데이터베이스에 대한 추가 및 갱신에 영향을 주지 않음 = false
    // 데이터베이스에 대한 추가 및 갱신에 영향을 줌 = true
    @ManyToOne
    @JoinColumn(name = "nth_no", referencedColumnName = "nth_no")
            /*insertable = false, updatable = false*/
    //@Column(name = "nthNo")
    private ProgressInfo nthNo; // 차시 관리 번호 (외래키 연결)

    //@OneToOne
    @Column(name = "lecture_no")
    //@JoinColumn(name = "lecture_no", referencedColumnName = "lecture_no"
            /*insertable = false, updatable = false*/
    private Long lectureNo; // 강의 번호 (외래키 연결)

    //@OneToOne
    //@JoinColumn(name = "member_no", referencedColumnName = "member_no")
            /*insertable = false, updatable = false*/
    @Column(name = "member_no")
    private Long memberNo; // 회원 번호 (외래키 연결)

    @Column(name = "content_no")
    private Long contentNo; // 접속 시작 시간

    @Column(name = "end_tm")
    private String endTm; // 접속 종료 시간

    @Column(name = "final_tm")
    @ColumnDefault("'0'")
    private String finalTm; // 강의 최종 재생 위치

    @Column(name = "max_tm")
    private String maxTm; // 강의 수강 종료 위치

    @Column(name = "prog_rt")
    private Integer progRt; // 차시 진도율

    @Builder
    public Progress(Long progressNo,
                    ProgressInfo nthNo,
                    Long lectureNo,
                    Long memberNo,
                    Long contentNo,
                    String endTm,
                    String finalTm,
                    String maxTm,
                    Integer progRt) {
        this.progressNo = progressNo;
        this.nthNo = nthNo;
        this.lectureNo = lectureNo;
        this.memberNo = memberNo;
        this.contentNo = contentNo;
        this.endTm = endTm;
        this.finalTm = finalTm;
        this.maxTm = maxTm;
        this.progRt = progRt;
    }

    // 팩토리메서드 형식으로 만들어 컨트롤러에서 객체 생성할 수 있게 해주기
    public static Progress progressTm() {
        return new Progress();
    }
}
