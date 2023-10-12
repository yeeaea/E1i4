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

import java.lang.reflect.Member;
import java.util.List;

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
    public boolean applyForLecture(LectureApplyDto lectureApplyDto) {
        Long lectureNo = lectureApplyDto.getLectureNo();
        Long memberNo = lectureApplyDto.getMemberNo();

        // 강의와 회원 엔터티 찾기
        LectureInfo lectureInfo = lectureInfoRepository.findById(lectureNo)
                .orElseThrow(() -> new IllegalArgumentException("강의 정보를 찾을 수 없습니다."));
        Members member = memberRepository.findById(memberNo)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        // 수강신청 엔터티 저장
        LectureApply lectureApply = LectureApply.builder()
                .lectureNo(lectureInfo)
                .memberNo(member)
                .completionYn(false)
                .build();

        lectureApplyRepository.save(lectureApply);

        return true; // 신청 성공
    }


    // 회원 번호로 수강 신청한 강의 목록 찾기
    public List<LectureApply> getLecturesByMember(Long memberNo) {
        return lectureApplyRepository.findByMemberNo_MemberNo(memberNo);
    }



}
