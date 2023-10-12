package org.online.lms.video.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table(name="content")
@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content { // 콘텐츠 관리 정보 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_mgmt_no", updatable = false)
    private long contentNo; // 콘텐츠 관리 번호

    @OneToOne
    @JoinColumn(name = "content_mgmt_no2", referencedColumnName = "content_mgmt_no")
    private Content contentNo2; // 콘텐츠 관리 번호 (재귀)

    @Column(name = "content_name", nullable = false)
    private String contentName; // 콘텐츠명

    @Column(name = "content_desc", length = 10000)
    private String contentDesc; // 콘텐츠 설명

    @Column(name = "content_file_no")
    private int contentFileNo; // 콘텐츠 파일 번호

    @Column(name = "ytb_url", nullable = false)
    private String ytbUrl; // Youtube 연동 번호

    @Column(name = "content_url")
    private String contentUrl; // 콘텐츠 호출 URL

    @Column(name = "run_tm")
    private int runTm; // 차시학습시간

    @Builder
    public Content(long contentNo,
                   Content contentNo2,
                   String contentName,
                   String contentDesc,
                   int contentFileNo,
                   String ytbUrl,
                   String contentUrl,
                   int runTm) {
        this.contentNo = contentNo;
        this.contentNo2 = contentNo2;
        this.contentName = contentName;
        this.contentDesc = contentDesc;
        this.contentFileNo = contentFileNo;
        this.ytbUrl = ytbUrl;
        this.contentUrl = contentUrl;
        this.runTm = runTm;
    }

    public static Content contentYtb() {
        return new Content();
    }
}
