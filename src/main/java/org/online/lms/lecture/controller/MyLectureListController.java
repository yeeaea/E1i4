package org.online.lms.lecture.controller;

import org.online.lms.lecture.domain.LectureApply;
import org.online.lms.lecture.domain.LectureInfo;
import org.online.lms.lecture.service.LectureApplyService;
import org.online.lms.lecture.service.LectureInfoService;
import org.online.lms.security.domain.Members;
import org.online.lms.security.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/lms/mypage")
public class MyLectureListController {

    private final LectureInfoService lectureInfoService;
    private final LectureApplyService lectureApplyService;

    private final MemberService memberService;

    @Autowired
    public MyLectureListController(LectureInfoService lectureInfoService,
                                   LectureApplyService lectureApplyService,
                                   MemberService memberService) {
        this.lectureInfoService = lectureInfoService;
        this.lectureApplyService = lectureApplyService;
        this.memberService = memberService;
    }

    @GetMapping("/lecture-all")
    public String viewMyLectures(Model model, Principal principal) {
        if (principal != null) {
            // 로그인 아이디 받아오기
            String loginId = principal.getName();

            // 로그인 아이디로 회원 번호 찾기
            Optional<Members> member =  memberService.findByLoginId(loginId);

            Long memberNo = member.get().getMemberNo();

            // 회원 번호로 수강 신청한 강의 찾기
            List<LectureApply> myLectures = lectureApplyService.getLecturesByMember(memberNo);

            // 사용자가 수강 신청한 강의의 번호를 저장할 리스트
            List<Long> lectureNumbers = new ArrayList<>();

            // 각 LectureApply 객체에서 강의 번호를 추출하여 리스트에 추가
            for (LectureApply lectureApply : myLectures) {
                Long lectureNo = lectureApply.getLectureNo().getLectureNo();
                lectureNumbers.add(lectureNo);
            }

            // 개설 강의 정보를 저장하 리스트
            List<LectureInfo> lectureDetails = new ArrayList<>();

            // 강의 번호 목록을 기반으로 개설 강의 정보 가져오기
            for (Long lectureNo : lectureNumbers) {
                LectureInfo lectureInfo = lectureInfoService.findById(lectureNo);
                lectureDetails.add(lectureInfo);
            }

            // 모델에 결과를 추가하여 뷰로 전달
            model.addAttribute("lectureDetails", lectureDetails);
        }
        // 결과 페이지의 뷰 이름 반환
        return "/page/lecture/myLectureList";
    }
}
