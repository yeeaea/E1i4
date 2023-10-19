package org.online.lms.video.controller;

import lombok.extern.slf4j.Slf4j;
import org.online.lms.security.domain.Members;
import org.online.lms.security.service.MemberService;
import org.online.lms.video.domain.Content;
import org.online.lms.video.domain.ProgressInfo;
import org.online.lms.video.service.ProgressTmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping("/lms/online")
@Controller
public class ProgressUserViewController {

    private final ProgressTmService progressTmService;
    private final MemberService memberService;
    @Autowired
    public ProgressUserViewController(
            ProgressTmService progressTmService,
            MemberService memberService) {
        this.progressTmService = progressTmService;
        this.memberService = memberService;
    }

    @GetMapping("/progress-info-list/{lectureNo}")
    public String ytbList(@PathVariable Long lectureNo, Model model) {
        // 온라인 강의 콘텐츠 리스트

        List<Content> contentList = progressTmService.findContentByLectureNo(lectureNo);
        model.addAttribute("contentList", contentList);
        return "/page/video/ytbContent";

    }

    @GetMapping("/view/{contentNo}")
    public String ytbPlay(@PathVariable Long contentNo, Model model) {
        // 온라인 강의 콘텐츠 리스트에서 재생버튼 누르면 나오는 영상 플레이 창

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginId = authentication.getName();
        Optional<Members> userOptional = memberService.findByLoginId(loginId);
        Members member = userOptional.get();
        Long memberNo = member.getMemberNo();
        model.addAttribute("memberNo", memberNo);


        String finalTmValue = progressTmService.findFinalTmByContentNoAndMemberNo(contentNo, memberNo);

        model.addAttribute("finalTmValue", finalTmValue);

        List<Content> contentList =
                progressTmService.findContentByContentNo(contentNo);
        model.addAttribute("contentList", contentList);

        List<ProgressInfo> progressInfos =
                progressTmService.findProgressInfosByContentNo(contentNo);
        model.addAttribute("progressInfos", progressInfos);

        return "/page/video/ytbView";
    }

}

