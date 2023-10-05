package org.online.lms.boards.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.online.lms.boards.domain.Post;

import java.time.LocalDateTime;
@NoArgsConstructor
@Getter
public class PostViewResponse {
    private Long postNo;
    private String postTitle;
    private String postContent;
    private LocalDateTime postRtm;
    // 파일 , 닉네임 추가예정

    public PostViewResponse(Post post){
        this.postNo = post.getPostNo();
        this.postTitle = post.getPostTitle();
        this.postContent = post.getPostContent();
        this.postRtm = post.getPostRtm();
    }

}
