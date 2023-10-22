package org.online.lms.video.service;

import lombok.extern.slf4j.Slf4j;
import org.online.lms.video.domain.Content;
import org.online.lms.video.domain.Progress;
import org.online.lms.video.domain.ProgressInfo;
import org.online.lms.video.dto.ProgressTmRequest;
import org.online.lms.video.repository.ProgressTmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class ProgressTmService {

    private final ProgressTmRepository progressTmRepository;

    @Autowired
    public ProgressTmService(ProgressTmRepository progressTmRepository) {
        this.progressTmRepository = progressTmRepository;
    }

    public Progress saveTmData(Progress progress) {
        return progressTmRepository.save(progress);
    }

    public Optional<Progress> findByProgressNo(Long progressNo) {
        return progressTmRepository.findByProgressNo(progressNo);
    }

    public void save(ProgressTmRequest req) {
    }


    public List<Long> findByLectureNo(Long lectureNo) {
        return progressTmRepository.findByLectureNo(lectureNo);
    }


    // 강의번호 통해 강의번호에 연결된 콘텐츠 전체 가져오기
    public List<Content> findContentByLectureNo(Long lectureNo) {
        return progressTmRepository.findContentByLectureNo(lectureNo);
    }

    // 콘텐츠 번호 통해 콘텐츠 전체 가져오기
    public List<Content> findContentByContentNo(Long contentNo) {
        return progressTmRepository.findContentByContentNo(contentNo);
    }

    // 콘텐츠 번호 통해 차시 전체 가져오기
    public List<ProgressInfo> findProgressInfosByContentNo(Long contentNo) {
        return progressTmRepository.findProgressInfosByContentNo(contentNo);
    }

    // '회원번호 + 콘텐츠번호 = 고유번호'를 통해서 finalTm 가져오기
    public String findFinalTmByContentNoAndMemberNo(Long contentNo, Long memberNo) {
        return progressTmRepository.findFinalTmByContentNoAndMemberNo(contentNo, memberNo);
    }

    // '회원번호 + 콘텐츠번호 = 고유번호'를 통해서 maxTm 가져오기
    public String findMaxTmByContentNoAndMemberNo(Long contentNo, Long memberNo) {
        return progressTmRepository.findMaxTmByContentNoAndMemberNo(contentNo, memberNo);
    }

   public List<Progress> findProgressByContentNoAndMemberNo(Long contentNo, Long memberNo) {
        return progressTmRepository.findProgressByContentNoAndMemberNo(contentNo, memberNo);
   }
}