package org.online.lms.video.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.online.lms.lecture.domain.LectureInfo;
import org.online.lms.video.domain.Content;
import org.online.lms.video.domain.ProgressInfo;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateProgressInfoRequest {

    private long nthNo; // 차시 관리 번호
    private LectureInfo lecture; // 강의 관리 번호
    private Content content; // 콘텐츠 관리 번호
    private String nthDuration; // 차시 순서

    public ProgressInfo toEntity() {
        return ProgressInfo.builder()
                .nthNo(nthNo)
                .lecture(lecture)
                .content(content)
                .nthDuration(nthDuration)
                .build();
    }
}
