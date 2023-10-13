package org.online.lms.lecture.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Table(name = "lecture_apply")
@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureApply {

    // 데이터베이스에 대한 추가 및 갱신에 영향을 주지 않음 = false
    // 데이터베이스에 대한 추가 및 갱신에 영향을 줌 = true

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apply_no")
    private Long applyNo; // 수강신청번호

    @Column(name = "lecture_no")
    private Long lectureNo; // 강의 번호 (외래키 연결하지 않고, 받아와서 입력)

    @Column(name = "member_no")
    private Long memberNo; // 회원 번호 (외래키 연결하지 않고, 받아와서 입력)

    @Column(name = "completion_yn", nullable = false)
    private boolean completionYn; // 수료 여부

    @Builder
    public LectureApply(Long lectureNo,
                        Long memberNo,
                        boolean completionYn) {
        this.lectureNo = lectureNo;
        this.memberNo = memberNo;
        this. completionYn = completionYn;
    }
}
