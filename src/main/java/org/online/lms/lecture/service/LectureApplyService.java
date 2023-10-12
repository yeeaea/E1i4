package org.online.lms.lecture.service;

import org.online.lms.lecture.domain.LectureApply;
import org.online.lms.lecture.domain.LectureInfo;
import org.online.lms.lecture.dto.LectureApplyDto;
import org.online.lms.lecture.repository.LectureApplyRepository;
import org.online.lms.lecture.repository.LectureInfoRepository;
import org.online.lms.security.domain.Members;
import org.online.lms.security.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class LectureApplyService {

    private final LectureApplyRepository lectureApplyRepository;
    private final LectureInfoRepository lectureInfoRepository;
    private final MemberRepository memberRepository;
    @Autowired
    public LectureApplyService(LectureApplyRepository lectureApplyRepository,
                               LectureInfoRepository lectureInfoRepository,
                               MemberRepository memberRepository) {
        this.lectureApplyRepository = lectureApplyRepository;
        this.lectureInfoRepository = lectureInfoRepository;
        this.memberRepository = memberRepository;
    }

    // 수강신청
    @Transactional
    public boolean applyForLecture(LectureApplyDto lectureApplyDto) {
        Long lectureNo = lectureApplyDto.getLectureNo();
        Long memberNo = lectureApplyDto.getMemberNo();

        if (lectureNo == null || memberNo == null) {
            return false;
        }

//        // 이미 수강신청한 경우 중복을 막음
//        if (isDuplicate(lectureNo, memberNo)) {
//            return false;
//        }

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


    // 회원 번호로 수강 신청한 강의 목록 찾기
    public List<LectureApply> getLecturesByMember(Long memberNo) {
        return lectureApplyRepository.findByMemberNo(memberNo);
    }



}
