package org.online.lms.lecture.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가
@Getter
@Setter
public class LectureApplyDto {
    private Long lectureNo;
    private Long memberNo;
    private boolean completionYn = false; // 처음 수강신청 시 false로 설정
}
