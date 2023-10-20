package org.online.lms.video.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.online.lms.video.domain.ProgressInfo;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgressTmRequest {

    private Long progressNo; // 세션키 통해서 insert말고 update
    private Long nthNo; // 콘텐츠 번호에 해당하는 progressInfo의 차시번호
    private Long contentNo;  // 콘텐츠 번호
    private Long lectureNo;  // 강의 번호
    private Long memberNo; // 로그인 아이디의 멤버 번호
    private String finalTm; // 5초에 한번씩 업데이트될 컬럼이자 시작 위치
    private String maxTm; // 최종 위치로 저장될 컬럼
    private Integer progRt;  // 진행률을 통해 출석 처리함(80% 이상일 때 자동 출석)
}
