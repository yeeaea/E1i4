package org.online.lms.video.controller;

import lombok.RequiredArgsConstructor;
import org.online.lms.lecture.domain.LectureInfo;
import org.online.lms.lecture.repository.LectureInfoRepository;
import org.online.lms.lecture.service.LectureInfoService;
import org.online.lms.video.domain.Content;
import org.online.lms.video.domain.ProgressInfo;
import org.online.lms.video.dto.AddProgressInfoRequest;
import org.online.lms.video.dto.ProgressInfoViewResponse;
import org.online.lms.video.dto.UpdateProgressInfoRequest;
import org.online.lms.video.service.ProgressInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/online")
public class ProgressInfoViewController {

    private final ProgressInfoService progressInfoService;

    @GetMapping("/progress-all")
    public String showProgressInfoList(Model model) {
        List<ProgressInfoViewResponse> progressInfoList = progressInfoService.findAll().stream()
                .map(ProgressInfoViewResponse::new)
                .toList();

        List<LectureInfo> lectureInfoList = progressInfoService.getAllLectureInfo();
        List<Content> contentList = progressInfoService.getAllContent();

        model.addAttribute("progressInfoList", progressInfoList);
        model.addAttribute("lectureInfoList", lectureInfoList);
        model.addAttribute("contentList", contentList);

        return "page/progress/progressInfoList";
    }

    @GetMapping("/api/progress")
    public List<LectureInfo> getAllLectureInfo() {
        return progressInfoService.getAllLectureInfo();
    }
}
