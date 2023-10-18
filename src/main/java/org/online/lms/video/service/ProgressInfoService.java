package org.online.lms.video.service;

import lombok.RequiredArgsConstructor;
import org.online.lms.lecture.domain.LectureInfo;
import org.online.lms.lecture.repository.LectureInfoRepository;
import org.online.lms.video.domain.Content;
import org.online.lms.video.domain.ProgressInfo;
import org.online.lms.video.dto.AddProgressInfoRequest;
import org.online.lms.video.dto.UpdateProgressInfoRequest;
import org.online.lms.video.repository.ProgressInfoRepository;
import org.online.lms.video.repository.VideoInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProgressInfoService {

    private final ProgressInfoRepository progressInfoRepository;
    private final LectureInfoRepository lectureInfoRepository;
    private final VideoInfoRepository videoInfoRepository;

    // 강의 차시 전체 조회
    @Transactional
    public List<ProgressInfo> findAll() {
        return progressInfoRepository.findAll();
    }

    // 차시 정보 조회
    @Transactional
    public ProgressInfo findById(long nthNo) {
        return progressInfoRepository.findById(nthNo)
                .orElseThrow(() -> new IllegalArgumentException("not found" + nthNo));
    }

    // 차시 번호로 차시 정보 얻기
    public ProgressInfo getProgressInfoByNthNo(long nthNo) {
        Optional<ProgressInfo> optionalProgressInfo = progressInfoRepository.findById(nthNo);

        if (optionalProgressInfo.isPresent()) {
            return optionalProgressInfo.get();
        } else {
            throw new IllegalArgumentException("해당 차시 정보가 존재하지 않습니다.");
        }
    }

    // 강의 정보 조회
    @Transactional
    public List<LectureInfo> getAllLectureInfo() {
        return lectureInfoRepository.findAll();
    }

    // 콘텐츠 정보 조회
    @Transactional
    public List<Content> getAllContent() {
        return videoInfoRepository.findAll();
    }

    // 차시 정보 추가
    @Transactional
    public ProgressInfo addProgress(AddProgressInfoRequest request) {
        ProgressInfo progressInfo = ProgressInfo.builder()
                .nthNo(request.getNthNo())
                .lecture(lectureInfoRepository.findById(request.getLectureNo())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid lectureNo")))
                .content(videoInfoRepository.findById(request.getContentNo())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid contentNo")))
                .build();

        return progressInfoRepository.save(progressInfo);
    }

    // 차시 정보 수정
    @Transactional
    public ProgressInfo updateProgress(long nthNo, int nthDuration, UpdateProgressInfoRequest request) {
        ProgressInfo progressInfo = progressInfoRepository.findById(nthNo).orElseThrow(
                () -> new IllegalArgumentException("해당 차시 정보가 존재하지 않습니다."));

        // 업데이트된 차시 정보 설정
        progressInfo.update(nthNo, nthDuration);

        Content content = progressInfo.getContent();
        content.setContentNo(request.getContent());

        LectureInfo lecture = progressInfo.getLecture();
        lecture.setLectureNo(request.getLecture());

        return progressInfoRepository.save(progressInfo);
    }

    // 차시 정보 삭제
    @Transactional
    public void deleteProgress(long nthNo) {
        progressInfoRepository.deleteById(nthNo);
    }
}
