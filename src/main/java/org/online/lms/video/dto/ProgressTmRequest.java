package org.online.lms.video.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.online.lms.video.domain.ProgressInfo;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgressTmRequest {

    private Long progressNo; // 기본키 통해서 insert말고 update
    private Long nthNo; // 콘텐츠 번호에 해당하는 progressInfo의 차시번호
    private Long memberNo; // 로그인 아이디 받아오기?
    private String finalTm; // 5초에 한번씩 업데이트될 컬럼
    private String maxTm; // 최종 위치로 저장될 컬럼
}
