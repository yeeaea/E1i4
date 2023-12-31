package org.online.lms.video.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.online.lms.lecture.domain.LectureInfo;
import org.online.lms.lecture.repository.LectureInfoRepository;
import org.online.lms.video.domain.Content;
import org.online.lms.video.domain.ProgressInfo;
import org.online.lms.video.repository.VideoInfoRepository;

@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가
@Getter
@Setter
public class AddProgressInfoRequest {
    private LectureInfoRepository lectureInfoRepository;
    private VideoInfoRepository videoInfoRepository;

    private long nthNo; // 차시 관리 번호
    private long lectureNo; // 강의 관리 번호
    private long contentNo; // 콘텐츠 관리 번호
    private String nthDuration; // 차시 순서

    public ProgressInfo toEntity() {

        LectureInfo lecture = lectureInfoRepository.findByLectureNo(lectureNo);
        Content content = videoInfoRepository.findByContentNo(contentNo);

        return ProgressInfo.builder()
                .nthNo(nthNo)
                .lecture(lecture)
                .content(content)
                .nthDuration(nthDuration)
                .build();
    }
}
