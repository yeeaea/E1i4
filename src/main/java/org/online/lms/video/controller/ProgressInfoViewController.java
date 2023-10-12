package org.online.lms.video.controller;

import lombok.RequiredArgsConstructor;
import org.online.lms.lecture.domain.LectureInfo;
import org.online.lms.lecture.repository.LectureInfoRepository;
import org.online.lms.lecture.service.LectureInfoService;
import org.online.lms.video.domain.Content;
import org.online.lms.video.domain.ProgressInfo;
import org.online.lms.video.dto.AddProgressInfoRequest;
import org.online.lms.video.dto.UpdateProgressInfoRequest;
import org.online.lms.video.service.ProgressInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/online")
public class ProgressInfoViewController {

    private final ProgressInfoService progressInfoService;
    private final LectureInfoService lectureInfoService;

    // 강의 목록 전제 조회
    @GetMapping("/progress-all")
    public String ProgressInfoList(Model model) {
        List<ProgressInfo> progressInfoList = progressInfoService.findAll();
        model.addAttribute("progressInfoList", progressInfoList);
        model.addAttribute("progressInfoRequest", new AddProgressInfoRequest());
        model.addAttribute("updateProgressRequest", new UpdateProgressInfoRequest());
        return "page/progress/progressInfoList";
    }

    @GetMapping("/progress-all/{nthNo}")
    public String allProgressInfo(@PathVariable long nthNo, Model model) {
        ProgressInfo progressInfo = progressInfoService.getProgressInfoById(nthNo);

        if (progressInfo != null) {
            model.addAttribute("progressInfo", progressInfo);

            // 강의 정보 조회
            LectureInfo lectureInfo = progressInfoService.getLectureInfoByProgressInfo(progressInfo);
            model.addAttribute("lectureInfo", lectureInfo);

            // 콘텐츠 정보 조회
            Content content = progressInfoService.getContentByProgressInfo(progressInfo);
            model.addAttribute("content", content);
        } else {
            model.addAttribute("progressInfo", null);
            model.addAttribute("lectureInfo", null);
            model.addAttribute("content", null);
        }

        return "page/progress/progressInfoList"; // 수정된 뷰 이름
    }
}
