package org.online.lms.boards.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.online.lms.boards.domain.Post;
import org.online.lms.boards.domain.FileUpload;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PostViewResponse {
    private Long postNo;
    private String postTitle;
    private String postContent;
    private LocalDateTime postRtm;
    private String orgFileName;
    private String saveFileName;
    private String filePath;
    private Long boardNo;
    private String boardType;
    private List<CommentListViewResponse> comments;
    // 파일 , 닉네임 추가예정

    public PostViewResponse(Post post, FileUpload file) {
        this.postNo = post.getPostNo();
        this.postTitle = post.getPostTitle();
        this.postContent = post.getPostContent();
        this.postRtm = post.getPostRtm();
        this.boardNo = post.getBoardNo();
        this.boardType = post.getBoardType();

        if (file != null) {
            this.orgFileName = file.getOrgFileName();
            this.saveFileName = file.getSaveFileName();
            this.filePath = file.getFilePath();
        }
    }



}
