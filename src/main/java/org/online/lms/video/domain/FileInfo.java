package org.online.lms.video.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table(name="file_info")
@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileInfo {

    @EmbeddedId
    private FileKey fileId; // 복합 기본키 (파일 번호, 파일 일련 번호)

    @Column(name = "org_file_name")
    private String originalFileName; // 원본 파일명

    @Column(name = "save_file_name", nullable = false)
    private String savedFileName; // 저장 파일명

    @Column(name = "file_path", nullable = false)
    private String filePath; // 파일 경로

    @Column(name = "file_size")
    private int fileSize; // 파일 크기

    @Builder
    public FileInfo(FileKey fileId,
                    String originalFileName,
                    String savedFileName,
                    String filePath,
                    int fileSize) {
        this.fileId = fileId;
        this.originalFileName = originalFileName;
        this.savedFileName = savedFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }
}
