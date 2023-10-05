package org.online.lms.boards.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "comment")
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_no", updatable = false)
    private Long commentNo;

    @Column(name = "comment_content", nullable = false)
    private String commentContent;

    @Column(name = "comment_rtm")
    private LocalDateTime commentRtm;

    @Column(name = "comment_utm")
    private LocalDateTime commentUtm;

    @Column(name = "parent_comment_no")
    private Long parentCommentNo;

    @ManyToOne
    @JoinColumn(name = "post_no")
    private Post post;
}
