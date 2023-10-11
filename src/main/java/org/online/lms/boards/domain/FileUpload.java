package org.online.lms.boards.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "file_board")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class FileUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_no", updatable = false)
    private Long fileNo;

    @Column(name = "file_seq")
    private Long fileSeq;

    @Column(name = "org_file_name")
    private String orgFileName;

    @Column(name = "save_file_name")
    private String saveFileName;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_size")
    private Long fileSize;

    // 기본 생성자를 명시적으로 추가

    @Builder
    public FileUpload(Long fileSeq, String orgFileName, String saveFileName, String filePath, Long fileSize) {
        this.fileSeq = fileSeq;
        this.orgFileName = orgFileName;
        this.saveFileName = saveFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }

    public void update(Long fileSeq, String orgFileName, String saveFileName, String filePath, Long fileSize) {
        this.fileSeq = fileSeq;
        this.orgFileName = orgFileName;
        this.saveFileName = saveFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }
}





