package org.online.lms.video.dto;

import lombok.Getter;
import lombok.Setter;
import org.online.lms.lecture.domain.LectureInfo;
import org.online.lms.video.domain.Content;
import org.online.lms.video.domain.ProgressInfo;

@Getter
@Setter
public class ProgressInfoViewResponse {
    private long nthNo; // 차시 관리 번호
    private LectureInfo lecture; // 강의 번호
    private Content content; // 콘텐츠 관리 번호
    private int nthDuration; // 차시 순서

    public ProgressInfoViewResponse(ProgressInfo progressInfo) {
        this.nthNo = progressInfo.getNthNo();
        this.lecture = progressInfo.getLecture();
        this.content = progressInfo.getContent();
        this.nthDuration = progressInfo.getNthDuration();
    }
}
