package org.online.lms.video.controller;


import org.online.lms.video.domain.Content;
import org.online.lms.video.dto.VideoInfoRequest;
import org.online.lms.video.dto.VideoInfoViewResponse;
import org.online.lms.video.service.VideoInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class VideoViewController {

    private final VideoInfoService videoInfoService;
    @Autowired
    public VideoViewController(VideoInfoService videoInfoService) {
        this.videoInfoService = videoInfoService;
    }

    @GetMapping("/contentList")
    public String contentList(Model model) {

        // 콘텐츠 단순 리스트 조회
        List<VideoInfoViewResponse> contentInfo = videoInfoService.findAll().stream()
                .map(VideoInfoViewResponse::new)
                .toList();
        model.addAttribute("contentInfo", contentInfo);

        return "/page/video/videoList";
    }

    @GetMapping("/content")
    public String contentListForm(Model model) {

        // 콘텐츠 리스트 + 등록창
        List<VideoInfoViewResponse> contentInfo = videoInfoService.findAll().stream()
                .map(VideoInfoViewResponse::new)
                .toList();
        model.addAttribute("contentInfo", contentInfo);

        return "/page/video/videoListForm";
    }

    @GetMapping("/content/save")
    public String contentListSave(Model model) {

        // 콘텐츠 저장하는 부분
        // !!! 주의 !!! 여기 uri는 프로그램 시작하고 딱 한번만 실행하기
        // uri 한번 치고 들어가서 새로고침 금지(계속 생성됨...)
        // uri통해서 들어가면 리스트에 아무것도 뜨지 않지만 데이터 저장됐습니다.
        // 다른 작업하고자 하는 부분으로 가서 작업하기~~
        List<VideoInfoViewResponse> contentInfo = videoInfoService.findAll().stream()
                .map(VideoInfoViewResponse::new)
                .toList();
        model.addAttribute("contentInfo", contentInfo);

        return "/page/video/videoListSave";
    }

    @GetMapping("/content-edit")
    public String contentRegister(Model model) {

        // 콘텐츠 리스트 + 등록 + 수정
        List<Content> contentList = videoInfoService.findAll();
        model.addAttribute("contentList", contentList);
        model.addAttribute("videoInfoRequest", new VideoInfoRequest());

        return "/page/video/videoUpdate";
    }

    /////// 추후에 viewController로 이동(Security) - 차시테이블 사용
    // 재생목록 리스트
    @GetMapping("/lms/online/progress-info-list")
    public String ytbList(Model model) {
        return "/page/video/ytbContent";
    }

    // 재생목록에서 버튼 누르면 나오는 콘텐츠 플레이 창
    @GetMapping("/lms/online/view")
    public String ytbPlay(Model model) {
        List<Content> contentList = videoInfoService.findAll();
        model.addAttribute("contentList", contentList);
        return "/page/video/ytbView";
    }
}
