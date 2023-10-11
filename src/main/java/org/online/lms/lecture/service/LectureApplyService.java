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

    public void applyForLecture(LectureApplyDto lectureApplyDto) {
        Long lectureNo = lectureApplyDto.getLectureNo();
        Long memberNo = lectureApplyDto.getMemberNo();
        boolean completionYn = lectureApplyDto.isCompletionYn(); // completionYn 값을 가져옴

        // 강의 번호와 회원 번호를 사용하여 해당 엔티티들을 찾습니다.
        LectureInfo lectureInfo = lectureInfoRepository.findById(lectureNo)
                .orElseThrow(() -> new IllegalArgumentException("강의 정보를 찾을 수 없습니다."));
        Members member = memberRepository.findById(memberNo)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        // 수강신청 정보를 생성하고 저장합니다.
        LectureApply lectureApply = LectureApply.builder()
                .lectureNo(lectureInfo)
                .memberNo(member)
                .completionYn(completionYn) // completionYn 값을 사용
                .build();

        lectureApplyRepository.save(lectureApply);
    }

    // 회원 번호로 수강 신청한 강의 목록 찾기
    public List<LectureApply> getLecturesByMember(Long memberNo) {
        return lectureApplyRepository.findByMemberNo_MemberNo(memberNo);
    }



}
