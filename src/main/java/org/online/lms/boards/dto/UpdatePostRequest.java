package org.online.lms.boards.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdatePostRequest {
    private String postTitle;
    private String postContent;
    private LocalDateTime postRtm;
    private LocalDateTime postUtm;
    // 파일 추가 예정
}
