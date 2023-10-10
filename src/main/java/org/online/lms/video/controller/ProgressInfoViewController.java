package org.online.lms.video.controller;

import lombok.RequiredArgsConstructor;
import org.online.lms.video.domain.ProgressInfo;
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
@RequestMapping("/lms/online")
public class ProgressInfoViewController {

    @Autowired
    private ProgressInfoService progressInfoService;

    @GetMapping("/progress-all")
    public String allProgressInfo(Model model) {
        List<ProgressInfo> progressInfoList = progressInfoService.getAllProgressInfo();
        model.addAttribute("progressInfoList", progressInfoList);
        return "page/progress/progressInfoList";
    }

    @GetMapping("/progress-info/{nthNo}")
    public String viewProgressInfo(@PathVariable long nthNo, Model model) {
        ProgressInfo progressInfo = progressInfoService.getProgressInfoById(nthNo);

        if (progressInfo != null) {
            model.addAttribute("progressInfo", progressInfo);
            model.addAttribute("lectureInfo", progressInfoService.getLectureInfoById(progressInfo));
            model.addAttribute("content", progressInfoService.getContentById(progressInfo));
        }

        return "page/progress/progressInfo";
    }
}
