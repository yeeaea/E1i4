package org.online.lms.lecture.service;

import lombok.RequiredArgsConstructor;
import org.online.lms.lecture.domain.LectureInfo;
import org.online.lms.lecture.dto.AddLectureInfoRequest;
import org.online.lms.lecture.repository.LectureInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LectureInfoService {

    @Autowired
    private final LectureInfoRepository lectureInfoRepository;

    // 강의 추가 메소드
    public LectureInfo save(AddLectureInfoRequest request) {
        return lectureInfoRepository.save(request.toEntity());
    }

    // 강의 목록 조회
    public List<LectureInfo> findAll() {
        return lectureInfoRepository.findAll();
    }


    // 강의 계획서 조회
    public LectureInfo findById(Long lectureNo) {
        return lectureInfoRepository.findById(lectureNo)
                .orElseThrow(() -> new IllegalArgumentException("not found" + lectureNo));
    }
}
