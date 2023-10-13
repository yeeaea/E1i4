package org.online.lms.video.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateProgressInfoRequest {
    private long nthNo; // 차시 관리 번호
    private String nthName; // 차시명
    private long content; // 콘텐츠 관리 번호
}
