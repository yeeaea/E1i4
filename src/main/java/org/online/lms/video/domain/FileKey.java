package org.online.lms.video.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;

@Table(name="file_info")
@Getter
@Embeddable // 복합 기본키 정의
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileKey implements Serializable {

    @Column(name = "file_no")
    private int fileNo; // 파일 번호

    @Column(name = "file_seq")
    private int fileSeq; // 파일 일련 번호

    @Builder
    public FileKey(int fileNo, int fileSeq) {
        this.fileNo = fileNo;
        this.fileSeq = fileSeq;
    }
}