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
    // longinId 추가예정

    public Comment toEntity() {
        return Comment.builder()
                .commentContent(commentContent)
                // longinId 추가예정
                .build();
    }

}
