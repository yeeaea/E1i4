package org.online.lms.video.dto;

import lombok.Getter;
import lombok.Setter;
import org.online.lms.video.domain.Content;
import org.online.lms.video.domain.ProgressInfo;

@Getter
@Setter
public class ProgressInfoViewResponse {
    private long nthNo; // 차시 관리 번호
    private long lectureNo; // 강의 번호
    private String nthName; // 차시명
    private long contentNo; // 콘텐츠 관리 번호

    public ProgressInfoViewResponse(ProgressInfo progressInfo) {
        this.nthNo = progressInfo.getNthNo();
        this.lectureNo = progressInfo.getLectureNo().getLectureNo();
        this.nthName = progressInfo.getNthName();
        this.contentNo = progressInfo.getContentNo().getContentNo();
    }
}
