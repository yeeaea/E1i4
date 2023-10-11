package org.online.lms.boards.dto;

import lombok.Getter;
import org.online.lms.boards.domain.Comment;

import java.time.LocalDateTime;

@Getter
public class CommentListViewResponse {
    private final Long commentNo;
    private final Long postNo;
    private final String commentContent;
    private final LocalDateTime commentRtm;
    private final LocalDateTime commentUtm;

    public CommentListViewResponse(Comment comment) {
        this.postNo = comment.getPost().getPostNo();
        this.commentNo = comment.getCommentNo();
        this.commentContent = comment.getCommentContent();
        this.commentRtm = comment.getCommentRtm();
        this.commentUtm = comment.getCommentUtm();
    }
}
