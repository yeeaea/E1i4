package org.online.lms.boards.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.online.lms.boards.domain.Post;
import org.online.lms.boards.domain.FileUpload;

import java.time.LocalDateTime;
@NoArgsConstructor
@Getter
public class PostViewResponse {
    private Long postNo;
    private String postTitle;
    private String postContent;
    private LocalDateTime postRtm;
    private String orgFileName;
    private String filePath;
    // 파일 , 닉네임 추가예정

    public PostViewResponse(Post post, FileUpload file) {
        this.postNo = post.getPostNo();
        this.postTitle = post.getPostTitle();
        this.postContent = post.getPostContent();
        this.postRtm = post.getPostRtm();

        if (file != null) {
            this.orgFileName = file.getOrgFileName();
            this.filePath = file.getFilePath();
        }
    }

}
