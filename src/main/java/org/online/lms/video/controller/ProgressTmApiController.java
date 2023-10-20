package org.online.lms.video.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.online.lms.video.domain.Progress;
import org.online.lms.video.dto.ProgressTmRequest;
import org.online.lms.video.service.ProgressTmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class ProgressTmApiController {
    private final ProgressTmService progressTmService;

    @Autowired
    public ProgressTmApiController(
            ProgressTmService progressTmService) {
        this.progressTmService = progressTmService;
    }

    private Map<String, Progress> progressMap = new HashMap<>();

    @PostMapping("/api/admin/saveYoutubeTm")
    public Progress saveOrUpdateProgress(@RequestBody ProgressTmRequest req,
                                         HttpSession session, Principal principal) {

        if (principal != null) {
            String loginId = principal.getName();
            String sessionID = session.getId();

            // 세션에서 nthNo 가져오기
            String sessionKey = "nthNo_" + sessionID;
            Long savedNthNo = (Long) session.getAttribute(sessionKey);

            Progress progress;

            // 세션에 저장된 키가 있을 때, 세션에 있는 nthNo 있을 때 progressNo에 중복 저장하기
            if (savedNthNo != null && savedNthNo == req.getNthNo()) {
                // 동일한 nthNo를 가진 세션의 경우
                // progress를 찾아 업데이트
                progress = progressMap.get(savedNthNo.toString());
                // finalTm와 maxTm 업데이트
                //progress.setMemberNo(loginId);
                //progress.setMemberNo(req.getMemberNo());
                progress.setFinalTm(req.getFinalTm());
                progress.setMaxTm(req.getMaxTm());
                progressTmService.saveTmData(progress);
            } else {
                // 새로운 nthNo를 세션에 저장
                session.setAttribute(sessionKey, req.getNthNo());

                // progress를 생성하고 맵에 추가
                progress = Progress.progressTm();
                //progress.setNthNo(req.getNthNo());
                //progress
                progress.setContentNo(req.getContentNo());
                progress.setLectureNo(req.getLectureNo());
                progress.setMemberNo(req.getMemberNo());
                progress.setFinalTm(req.getFinalTm());
                progress.setMaxTm(req.getMaxTm());
                progressMap.put(req.getNthNo().toString(), progress);
                return progressTmService.saveTmData(progress);
            }

            return progress;
        }
        return null;
    }
}