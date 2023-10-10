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

    @ManyToOne
    @JoinColumn(name = "lecture_no", referencedColumnName = "lecture_no",
                insertable = false, updatable = false)
    private LectureInfo lectureNo; // 강의 번호 (외래키 연결)

    @Column(name = "nth_name")
    private String nthName; // 차시명

    @OneToOne
    @JoinColumn(name = "content_mgmt_no", referencedColumnName = "content_mgmt_no",
                insertable = false, updatable = false)
    private Content contentNo; // 콘텐츠 관리 번호 (외래키 연결)

    @Builder
    public ProgressInfo(String nthName) {
        this.nthName = nthName;
    }
}
