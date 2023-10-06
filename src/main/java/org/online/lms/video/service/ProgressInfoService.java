package org.online.lms.video.service;

import lombok.RequiredArgsConstructor;
import org.online.lms.lecture.domain.LectureInfo;
import org.online.lms.lecture.repository.LectureInfoRepository;
import org.online.lms.video.domain.Content;
import org.online.lms.video.domain.ProgressInfo;
import org.online.lms.video.repository.ProgressInfoRepository;
import org.online.lms.video.repository.VideoInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProgressInfoService {

    @Autowired
    private ProgressInfoRepository progressInfoRepository;

    public List<ProgressInfo> getAllProgressInfo() {
        return progressInfoRepository.findAll();
    }

    public ProgressInfo getProgressInfoById(long nthNo) {
        return progressInfoRepository.findById(nthNo).orElse(null);
    }

    public LectureInfo getLectureInfoById(ProgressInfo progressInfo) {
        return progressInfo.getLectureNo();
    }

    public Content getContentById(ProgressInfo progressInfo) {
        return progressInfo.getContentNo();
    }
}
