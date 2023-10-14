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

    // 강의 전체 조회
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
        ProgressInfo progressInfo = request.toEntity();
        return progressInfoRepository.save(progressInfo);
    }

    // 차시 정보 수정
    @Transactional
    public ProgressInfo updateProgress(long nthNo, UpdateProgressInfoRequest request) {
        Optional<ProgressInfo> optionalExistingProgress = progressInfoRepository.findById(nthNo);

        if(optionalExistingProgress.isPresent()) {
            ProgressInfo existingProgress = optionalExistingProgress.get();

            ProgressInfo updateProgress = ProgressInfo.builder()
                    .nthNo(existingProgress.getNthNo())
                    .content(existingProgress.getContent())
                    .build();

            return progressInfoRepository.save(updateProgress);
        } else {
            return null;
        }
    }

    // 차시 정보 삭제
    @Transactional
    public void deleteProgress(long nthNo) {
        progressInfoRepository.deleteById(nthNo);
    }
}
