package org.online.lms.boards.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.online.lms.boards.domain.Post;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostRequest {
    private String postTitle;
    private String postContent;

    // 게시판 분류, 파일 추가 예정
    // private String loginId;
    public Post toEntity() {
        return Post.builder()
                .postTitle(postTitle)
                .postContent(postContent)
                .build();
    }
}
