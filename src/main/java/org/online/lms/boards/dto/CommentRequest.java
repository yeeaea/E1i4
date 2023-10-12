package org.online.lms.boards.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.online.lms.boards.domain.Comment;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentRequest {
    private Long postNo;
    private String commentContent;
    private String loginId;

    public Comment toEntity() {
        return Comment.builder()
                .commentContent(commentContent)
                .loginId(loginId)
                .build();
    }

}
