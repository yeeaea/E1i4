
package org.online.lms.boards.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "post")
@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_no", updatable = false)
    private Long postNo;

    @Column(name = "post_title", nullable = false)
    private String postTitle;

    @Column(name = "post_content", nullable = false)
    private String postContent;

    @CreatedDate
    @Column(name = "post_rtm")
    private LocalDateTime postRtm;

    @LastModifiedDate
    @Column(name = "post_utm")
    private LocalDateTime postUtm;

    @Column(name = "post_view", columnDefinition = "INT DEFAULT 0")
    private int postView;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_no")
    private FileUpload file;

//    // 파일 목록 관리
//    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<FileUpload> files;
//

//    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<FileUpload> files;


    /*
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "board_no")
    private Board board;

    @Column(name = "login_id")
    private String loginId;

    */

    // 게시판 분류, 파일, 닉네임 추가 예정
    @Builder
    public Post(String postTitle, String postContent) {
        this.postTitle = postTitle;
        this.postContent = postContent;
    }
    // 수정 메서드
    public void update(String postTitle, String postContent) {
        this.postTitle = postTitle;
        this.postContent = postContent;
    }

    public void setFileSeq(long fileSeq) {
    }

    public void setOrgFileName(String orgFilename) {
    }

    public void setSaveFileName(String saveFileName) {
    }

    public void setFilePath(String filePath) {
    }

    public void setFileSize(long size) {
    }
}
