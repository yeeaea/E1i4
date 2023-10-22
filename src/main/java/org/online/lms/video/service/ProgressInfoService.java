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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProgressInfoService {

    private final ProgressInfoRepository progressInfoRepository;
    private final LectureInfoRepository lectureInfoRepository;
    private final VideoInfoRepository videoInfoRepository;

    // 강의 차시 전체 조회
    @Transactional
    public List<ProgressInfo> findAll() {
        List<ProgressInfo> progressInfoList = progressInfoRepository.findAll();
        progressInfoList.sort(Comparator.comparing(ProgressInfo::getNthDuration));
        return progressInfoList;
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
        LectureInfo lecture = lectureInfoRepository.findByLectureNo(request.getLectureNo());
        if (lecture == null) {
            throw new IllegalArgumentException("Invalid lectureNo");
        }

        Content content = videoInfoRepository.findByContentNo(request.getContentNo());
        if (content == null) {
            throw new IllegalArgumentException("Invalid contentNo");
        }

        ProgressInfo progressInfo = ProgressInfo.builder()
                .nthNo(request.getNthNo())
                .lecture(lecture)
                .content(content)
                .nthDuration(request.getNthDuration())
                .build();

        return progressInfoRepository.save(progressInfo);
    }

    // 차시 정보 수정
    @Transactional
    public ProgressInfo updateProgress(long nthNo, UpdateProgressInfoRequest request) {
        ProgressInfo progressInfo = progressInfoRepository.findById(nthNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 차시 정보가 존재하지 않습니다."));

        progressInfo.update(request.getLecture(),
                request.getContent(),
                request.getNthDuration());

        progressInfoRepository.save(progressInfo);

        return progressInfo;
    }

    // 차시 정보 삭제
    @Transactional
    public void deleteProgress(long nthNo) {
        progressInfoRepository.deleteById(nthNo);
    }

    // 콘텐츠 정보 조회
    @Transactional
    public Page<Content> getAllContent(Pageable pageable) {
        return videoInfoRepository.findAll(pageable);
    }
}
