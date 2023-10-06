package org.online.lms.video.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.online.lms.video.domain.Progress;
import org.online.lms.video.domain.ProgressInfo;

@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가
@Getter
@Setter
public class ProgressInfoRequest {

    private long nthNo; // 차시 관리 번호
    private long lectureNo; // 강의 번호 (강의명 사용)
    private String nthName; // 차시명
    private long contentNo; // 콘텐츠 관리 번호 (차시 학습 시간 사용)

    public ProgressInfo toEntity() {
        return ProgressInfo.builder()
                .nthName(nthName)
                .build();
    }
}
