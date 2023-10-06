package org.online.lms.admin.video.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.online.lms.video.domain.Content;
@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가
@Getter
@Setter
public class VideoInfoRequest {

    private long contentNo; // 콘텐츠 관리 번호
    private Content contentNo2; // 재귀 콘텐츠 관리 번호
    private String contentName; // 콘텐츠명
    private String contentDesc; // 콘텐츠 설명
    private int contentFileNo; // 콘텐츠 파일번호
    private String ytbUrl; // Youtube 연동번호
    private String contentUrl; // 콘텐츠 호출 URL
    private int runTm; // 차시학습시간

    public Content toEntity() { // 생성자를 사용해 객체 생성
        return Content.builder()
                .contentNo(contentNo)
                .contentNo2(contentNo2)
                .contentName(contentName)
                .contentDesc(contentDesc)
                .contentFileNo(contentFileNo)
                .ytbUrl(ytbUrl)
                .contentUrl(contentUrl)
                .runTm(runTm)
                .build();
    }

}
