package org.online.lms.lecture.controller;

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

            // 회원 번호로 수강 신청한 강의번호 찾기
            List<Long> lectureNos = lectureApplyService.findLectureNumbersByMemberNo(memberNo);

            // 강의 번호를 이용하여 강의 정보 가져오기
            List<LectureInfo> lectures = new ArrayList<>();
            for (Long lectureNo : lectureNos) {
                LectureInfo lecture = lectureInfoService.getLectureInfoByLectureNo(lectureNo);
                lectures.add(lecture);
            }

            // 모델에 결과를 추가하여 뷰로 전달
            model.addAttribute("lectures", lectures);
        }
        // 결과 페이지의 뷰 이름 반환
        return "/page/lecture/myLectureList";
    }
}
