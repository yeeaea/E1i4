package org.online.lms.boards.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Table(name = "comment")
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_no", nullable = false)
    private Long commentNo;

    @Column(name = "comment_content", nullable = false)
    private String commentContent;

    @CreatedDate
    @Column(name = "comment_rtm")
    private LocalDateTime commentRtm;

    @LastModifiedDate
    @Column(name = "comment_utm")
    private LocalDateTime commentUtm;

//    @Column(name = "parent_comment_no")
//    private Long parentCommentNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_no")
    private Post post;

    // 유저 추가해야 함..

    @Builder
    public Comment(String commentContent) {
        this.commentContent = commentContent;
       // this.loginId = loginId;
    }

    public void update(String commentContent){
        this.commentContent = commentContent;
    }



}
