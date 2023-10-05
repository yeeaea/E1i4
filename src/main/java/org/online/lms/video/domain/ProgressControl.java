package org.online.lms.video.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table(name="progress_control")
@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProgressControl { // 강의 차시 통제 정보 테이블

    // 데이터베이스에 대한 추가 및 갱신에 영향을 주지 않음 = false
    // 데이터베이스에 대한 추가 및 갱신에 영향을 줌 = true
    @Id
    @OneToOne
    @JoinColumn(name = "nth_no", referencedColumnName = "nth_no"
                /*insertable = false, updatable = false*/)
    private ProgressInfo nthNo; // 차시 관리 번호 (외래키 연결)

    @Column(name = "stop_tm", nullable = false)
    private String stopTm; // 강의 정지 위치

    @Column(name = "pop_msg")
    private String popMsg; // 호출 팝업 메세지

    @Column(name = "pop_ans")
    private String popAns; // 호출 팝업 정답

    @Column(name = "pop_id")
    private String popId; // 호출 팝업 ID

    @Builder
    public ProgressControl(ProgressInfo nthNo,
                           String stopTm,
                           String popMsg,
                           String popAns,
                           String popId) {
        this.nthNo = nthNo;
        this.stopTm = stopTm;
        this.popMsg = popMsg;
        this.popAns = popAns;
        this.popId = popId;
    }
}
