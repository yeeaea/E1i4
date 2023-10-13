package org.online.lms.video.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ytbDTO {

    private String videoTitle; // 콘테츠 명
    private String videoDescription; // 콘텐츠 설명
    private String videoId; // 유튜브 연동번호
    private String videoUrl; // 유튜브 url
    private int duration; // 재생 시간
}
