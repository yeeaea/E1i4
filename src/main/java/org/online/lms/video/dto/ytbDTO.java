package org.online.lms.video.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ytbDTO {
    private String videoTitle;
    private String videoDescription;
    private String videoId;
    private String videoUrl;
}
