package org.online.lms.video.controller;

import lombok.RequiredArgsConstructor;
import org.online.lms.video.domain.ProgressInfo;
import org.online.lms.video.dto.AddProgressInfoRequest;
import org.online.lms.video.dto.UpdateProgressInfoRequest;
import org.online.lms.video.service.ProgressInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/online")
public class ProgressInfoViewController {

    @Autowired
    private final ProgressInfoService progressInfoService;

    @GetMapping("/progress-all")
    public String ProgressInfoList(Model model) {
        List<ProgressInfo> progressInfoList = progressInfoService.findAll();
        model.addAttribute("progressInfoList", progressInfoList);
        model.addAttribute("progressInfoRequest", new AddProgressInfoRequest());
        model.addAttribute("updateProgressRequest", new UpdateProgressInfoRequest());
        return "page/progress/progressInfoList";
    }
}
