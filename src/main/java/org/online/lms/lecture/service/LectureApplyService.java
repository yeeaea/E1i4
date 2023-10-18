package org.online.lms.lecture.service;

import org.online.lms.lecture.domain.LectureApply;
import org.online.lms.lecture.dto.LectureApplyDto;
import org.online.lms.lecture.repository.LectureApplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LectureApplyService {

    private final LectureApplyRepository lectureApplyRepository;
    @Autowired
    public LectureApplyService(LectureApplyRepository lectureApplyRepository) {
        this.lectureApplyRepository = lectureApplyRepository;
    }

    // 수강신청
    @Transactional
    public boolean applyForLecture(LectureApplyDto lectureApplyDto) {
        Long lectureNo = lectureApplyDto.getLectureNo();
        Long memberNo = lectureApplyDto.getMemberNo();

        if (lectureNo == null || memberNo == null) {
            return false;
        }

        // 수강신청 엔터티 저장
        LectureApply lectureApply = LectureApply.builder()
                .lectureNo(lectureNo)
                .memberNo(memberNo)
                .completionYn(false)
                .build();

        lectureApplyRepository.save(lectureApply);

        return true; // 신청 성공
    }

    // 중복 수강신청 확인
    public boolean isDuplicate(Long lectureNo, Long memberNo) {
        return lectureApplyRepository.isDuplicate(lectureNo, memberNo);
    }

    // 회원 번호로 수강 신청한 강의 번호 리스트 받아오기
    public List<Long> findLectureNumbersByMemberNo(Long memberNo) {
        return lectureApplyRepository.findLectureNumbersByMemberNo(memberNo);
    }

    // 수료여부 확인
    public boolean isLectureCompleted(Long lectureNo, Long memberNo) {
        return lectureApplyRepository.existsByLectureNoAndMemberNoAndCompletionYnTrue(lectureNo, memberNo);
    }


}
