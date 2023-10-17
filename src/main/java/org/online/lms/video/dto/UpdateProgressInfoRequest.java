package org.online.lms.video.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.online.lms.video.domain.ProgressInfo;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateProgressInfoRequest {
    private long nthNo; // 차시 관리 번호
//    private long lecture; // 강의 관리 번호
    private long content; // 콘텐츠 관리 번호
}
