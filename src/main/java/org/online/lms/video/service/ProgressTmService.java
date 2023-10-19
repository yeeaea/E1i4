package org.online.lms.video.service;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.online.lms.boards.domain.Post;
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

    // 콘텐츠 번호 통해 Progress 전체 가져오기
//    public List<ProgressTmRequest> findProgressTmRequestByContentNo(Long contentNo) {
//        return progressTmRepository.findProgressTmRequestByContentNo(contentNo);
//    }

    // 세션을 통해 저장 progressNo 저장
    public Progress progressNoSave(Long progressNo, HttpSession session, ProgressTmRequest req) {

        // progressNo 있는지 확인하기 위해 번호 찾기
        Optional<Progress> progNo = progressTmRepository.findById(progressNo);
        // Progress 번호 추출해 progress 변수에 할당
        Progress progress = progNo.get();

        // 중복 저장 방지 위한 progressNo 마다 고유한 세션 키 생성
        String showKey = "show_video_" + progressNo;
        // 세션 키가 있는지 없는지에 대한 여부를 나타내는 boolean 값
        Boolean hasShow = (Boolean) session.getAttribute(showKey);
        // 세션 키가 없거나 예전의 기록이 있는 경우
        if(hasShow == null || hasShow) {
            progress.setNthNo(req.getNthNo());
            progress.setFinalTm(req.getFinalTm());
            progress.setMaxTm(req.getMaxTm());
            progressTmRepository.save(progress);
            // 세션에 해당 키를 저장해 중복 저장이 가능하게???
            session.setAttribute(showKey, true);
        }

        return progress;
    }
}