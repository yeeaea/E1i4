package org.online.lms.boards.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.online.lms.boards.domain.FileUpload;
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
    private FileUpload file;
}
