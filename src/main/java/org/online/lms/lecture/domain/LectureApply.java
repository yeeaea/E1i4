package org.online.lms.lecture.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.online.lms.security.domain.Members;
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
    @OneToOne
    @JoinColumn(name = "lecture_no", referencedColumnName = "lecture_no"
                /*insertable = false, updatable = false*/)
    private LectureInfo lectureNo; // 강의 번호 (외래키 연결)

    @OneToOne
    @JoinColumn(name = "member_no", referencedColumnName = "member_no"
                /*insertable = false, updatable = false*/)
    private Members memberNo; // 회원 번호 (외래키 연결)

    @Column(name = "completion_yn", nullable = false)
    private boolean completionYn; // 수료 여부

    @Builder
    public LectureApply(LectureInfo lectureNo,
                        Members memberNo,
                        boolean completionYn) {
        this.lectureNo = lectureNo;
        this.memberNo = memberNo;
        this. completionYn = completionYn;
    }
}
