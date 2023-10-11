package org.online.lms.boards.dto;

import lombok.Getter;
import org.online.lms.boards.domain.Comment;

import java.time.LocalDateTime;
@Getter
public class CommentResponse {
    private String commentContent;
    private LocalDateTime commentRtm;
    private LocalDateTime commentUtm;

    public CommentResponse(Comment comment) {
        this.commentContent = comment.getCommentContent();
        this.commentRtm = comment.getCommentRtm();
        this.commentUtm = comment.getCommentRtm();
    }

}
