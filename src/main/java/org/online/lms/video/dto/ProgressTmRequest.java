package org.online.lms.video.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgressTmRequest {

    private Long progressNo; // 기본키 통해서 insert말고 update
    private String finalTm; // 5초에 한번씩 업데이트될 컬럼
    private String maxTm; // 최종 위치로 저장될 컬럼
}
