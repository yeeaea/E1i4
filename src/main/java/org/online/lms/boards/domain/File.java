package org.online.lms.boards.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "file")
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File {

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
}
