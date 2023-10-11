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
public class UpdateCommentRequest {
    private String commentContent;
    private LocalDateTime commentRtm;
    private LocalDateTime commentUtm;
}
