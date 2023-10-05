package org.online.lms.boards.dto;

import lombok.Getter;
import org.online.lms.boards.domain.Post;

import java.time.LocalDateTime;

@Getter
public class PostListViewResponse {
    private Long postNo;
    private String postTitle;
    private String postContent;
    private LocalDateTime postRtm;
    private LocalDateTime postUtm;
    private int postView;

    // 파일 추가
    public PostListViewResponse(Post post){
        this.postNo = post.getPostNo();
        this.postTitle = post.getPostTitle();
        this.postContent = post.getPostContent();
        this.postRtm = post.getPostRtm();
        this.postUtm = post.getPostUtm();
        this.postView = post.getPostView();

    }

}
