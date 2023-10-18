package org.online.lms.video.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.online.lms.video.domain.Content;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoYtbRequest {

    //private Long contentNo; // 기본키 통해서 insert 대신 update
    private String videoTitle; // 콘텐츠 명
    private String videoDescription; // 콘텐츠 설명
    private String videoId; // 유튜브 연동번호
    private String videoUrl; // 유튜브 url
    private int duration; // 재생 시간

    public Content toEntity() { // 생성자를 사용해 객체 생성
        return Content.builder()
                .contentName(videoTitle)
                .contentDesc(videoDescription)
                .ytbUrl(videoId)
                .contentUrl(videoUrl)
                .runTm(duration)
                .build();
    }
}
