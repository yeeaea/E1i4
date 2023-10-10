package org.online.lms.boards.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "file_board")
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "post_no") // post_no 필드를 사용하여 게시물과 연결합니다.
//    private Post post;
// 생성자 및 빌더 수정
@Builder
public FileUpload(Long fileNo, Long fileSeq, String orgFileName, String saveFileName, String filePath, Long fileSize) {
    this.fileNo = fileNo;
    this.fileSeq = fileSeq;
    this.orgFileName = orgFileName;
    this.saveFileName = saveFileName;
    this.filePath = filePath;
    this.fileSize = fileSize;
}
}




