package org.online.lms.boards.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.online.lms.boards.domain.Board;
import org.online.lms.boards.domain.FileUpload;
import org.online.lms.boards.domain.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostRequest {
    private String postTitle;
    private String postContent;
    private Long fileNo;
    private Long fileSeq;
    private String orgFileName;
    private String saveFileName;
    private String filePath;
    private Long fileSize;
    private String loginId;
    private Long boardNo;
    private String boardType;

    private List<MultipartFile> files; // 여러 개의 파일을 업로드하기 위한 목록



    public Post toEntity() {
        FileUpload file = FileUpload.builder()
                .fileSeq(fileSeq)
                .orgFileName(orgFileName)
                .saveFileName(saveFileName)
                .filePath(filePath)
                .fileSize(fileSize)
                .build();


        Post post = Post.builder()
                .postTitle(postTitle)
                .postContent(postContent)
                .loginId(loginId)
                .boardNo(boardNo)
                .boardType(boardType)
                .build();

        post.setFile(file); // File 객체를 Post에 설정

        return post;
    }

}
