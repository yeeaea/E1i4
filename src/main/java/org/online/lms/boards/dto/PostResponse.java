package org.online.lms.boards.dto;

import lombok.Getter;
import org.online.lms.boards.domain.Post;

import java.time.LocalDateTime;
@Getter
public class PostResponse {
    private String postTitle;
    private String postContent;
    private LocalDateTime postRtm;
    private LocalDateTime postUtm;

    public PostResponse(Post post){
        this.postTitle = post.getPostTitle();
        this.postContent = post.getPostContent();
        this.postRtm = post.getPostRtm();
        this.postUtm  = post.getPostUtm();
    }

}
