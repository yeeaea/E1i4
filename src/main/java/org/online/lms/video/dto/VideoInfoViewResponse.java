package org.online.lms.video.dto;

import lombok.Getter;
import lombok.Setter;
import org.online.lms.video.domain.Content;

@Getter
@Setter
public class VideoInfoViewResponse {

    private long contentNo; // 콘텐츠 관리 번호
    private Content contentNo2; // 재귀 콘텐츠 관리 번호
    private String contentName; // 콘텐츠명
    private String contentDesc; // 콘텐츠 설명
    private int contentFileNo; // 콘텐츠 파일번호
    private String ytbUrl; // Youtube 연동번호
    private String contentUrl; // 콘텐츠 호출 URL
    private String runTm; // 차시학습시간

    public VideoInfoViewResponse(Content content) {

        this.contentNo = content.getContentNo();
        this.contentNo2 = content.getContentNo2();
        this.contentName = content.getContentName();
        this.contentDesc = content.getContentDesc();
        this.contentFileNo = content.getContentFileNo();
        this.ytbUrl = content.getYtbUrl();
        this.contentUrl = content.getContentUrl();
        this.runTm = content.getRunTm();
    }

}
