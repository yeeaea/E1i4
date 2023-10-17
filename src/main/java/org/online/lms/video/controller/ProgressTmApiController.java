package org.online.lms.video.controller;

import lombok.extern.slf4j.Slf4j;
import org.online.lms.lecture.domain.LectureApply;
import org.online.lms.lecture.domain.LectureInfo;
import org.online.lms.lecture.repository.LectureApplyRepository;
import org.online.lms.lecture.service.LectureApplyService;
import org.online.lms.security.domain.Members;
import org.online.lms.security.service.MemberService;
import org.online.lms.video.domain.Content;
import org.online.lms.video.domain.Progress;
import org.online.lms.video.domain.ProgressInfo;
import org.online.lms.video.dto.ProgressTmRequest;
import org.online.lms.video.repository.ProgressInfoRepository;
import org.online.lms.video.service.ProgressInfoService;
import org.online.lms.video.service.ProgressTmService;
import org.online.lms.video.service.VideoInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class ProgressTmApiController {
    private final ProgressTmService progressTmService;
    private final LectureApplyService lectureApplyService;
    private final MemberService memberService;
    private final LectureApplyRepository lectureApplyRepository;
    private final ProgressInfoService progressInfoService;
    private final VideoInfoService videoInfoService;
    @Autowired
    public ProgressTmApiController(
            ProgressTmService progressTmService,
            LectureApplyService lectureApplyService,
            MemberService memberService,
            LectureApplyRepository lectureApplyRepository,
            ProgressInfoService progressInfoService,
            VideoInfoService videoInfoService) {
        this.progressTmService = progressTmService;
        this.lectureApplyService = lectureApplyService;
        this.memberService = memberService;
        this.lectureApplyRepository = lectureApplyRepository;
        this.progressInfoService = progressInfoService;
        this.videoInfoService = videoInfoService;
    }

//    @PostMapping("/api/admin/saveYoutubeTm")
//    public Progress progressTm(@RequestBody ProgressTmRequest dto, Model model) {
//        // 재생 시간 저장
//        Progress videoData = Progress.progressTm();
//        videoData.setProgressNo(dto.getProgressNo());
//        //videoData.setVideoId(videoId);
//        videoData.setFinalTm(dto.getFinalTm());
//        videoData.setMaxTm(dto.getMaxTm());
//
//        model.addAttribute("videoData", videoData);
//        return progressTmService.saveTmData(videoData);
//    }

    @PostMapping("/api/admin/saveYoutubeTm")
    public ResponseEntity<String> saveOrUpdateProgress(@RequestBody ProgressTmRequest req, Principal principal) {

        // 로그인 아이디 받아오기
        String loginId = principal.getName();

        // 로그인 아이디로 회원 번호 찾기
        Optional<Members> member =  memberService.findByLoginId(loginId);

        Long memberNo = member.get().getMemberNo();

        // 회원 번호로 수강 신청한 강의번호 찾기
        // 여기서 가져온 강의번호가
        List<Long> lectureNos = lectureApplyService.findLectureNumbersByMemberNo(memberNo);
        log.info(lectureNos + "강의번호 찾아야지");

        List<LectureApply> lectureApplies = lectureApplyRepository.findByLectureNoIn(lectureNos);
        for (LectureApply lectureApply : lectureApplies) {
            //LectureInfo lecture = lectureApply.getLecture(); // 연관된 Lecture 정보 가져오기
        //log.info(lecture + "수강신청한 강의목록 가져오기!");
            // 이제 Lecture 엔티티의 필드 정보를 사용할 수 있습니다
            // 예: String lectureName = lecture.getLectureName();
        }

        List<ProgressInfo> progressInfos = progressInfoService.findAll();
        for(ProgressInfo progressInfo : progressInfos) {
            // 여기서 가져온 강의 번호와 같은 경우 for문으로 콘텐츠 목록을 돌아가면서 전체 리스트 뽑고
            // 그 중에서 연결된 차시를 뽑아서 가져오는 거야 (아니 그러면 nth_no 가져오면 되잖아)
            // 그래 다시 생각해보자.. nth_no를 가져올 건데,, 거기 있는 강의번호가 수강신청한 강의번호와 같은 경우
            ///// 내가 필요한 거 : 통제를 위한 콘텐츠 영상 1개
            ///// 통제 : 수강자 당 영상 통제 기록 1개 (progressNo에 저장)
            // 사용자가 듣는 강의가 필요하므로 수강신청한 강의들을 가져와야하고
            // 그 신청한 강의 목록 들 중에서 1개의 강의를 가져오기 -> 이거는 지금 현재 재생하는 강의에 대한 정보
            // 수강신청한 강의 번호 lectureNos(재생중인 강의번호) == lectureByPi 차시 테이블의 강의 번호
            // -> 콘텐츠 번호를 가져오기 contentNo
            // 수강신청한 강의와 콘텐츠가 연결된 차시 테이블의 기본키?
            LectureInfo lectureByPi = progressInfo.getLecture();
            Long lectureNo = lectureByPi.getLectureNo();

            Content contentByPi = progressInfo.getContent();
            Long contentNo = contentByPi.getContentNo();

            log.info(lectureNo + " : 차시 테이블의 강의 읽어오기");
            log.info(contentNo + " : 차시 테이블의 콘텐츠를 읽어오기");
            // 차시 테이블의 구조를 봤을 때,
            // 강의와 차시는 1대1 -> 수강신청한 강의번호를 가져오기
            // 강의와 콘텐츠는 1대다 => 차시를 통해서 강의번호를 가져오고 그 강의번호와 위에 가져온 강의 번호가
            // 일치하는 경우, 콘텐츠 번호를 가져오기(콘텐츠는 리스트로 .get()해서 가져오기)
        }

        // 콘텐츠 테이블을 통해서 차시 가져오기
        List<Content> contents = videoInfoService.findAll();
        for(Content content : contents) {
            // 이거는 연결 컬럼이 안 되어있어서 Progressinfo에서 못뽑음
            // Long으로 그냥 하나 뽑아야 되는 거...
            Long progressInfo = content.getContentNo();
        }
        // 강의 하나 콘텐츠 여러개 -> 내가 가져올 거는 콘텐츠(강의한개)
        // -> 배열로 뽑아보자(contentList.get(0).getYtbUrl()이런 형식으로)

        try {
            // progressNo로 레코드를 찾고, 없으면 새로운 레코드를 생성하거나, 있는 경우 해당 레코드를 업데이트합니다.
            Progress existingProgress = progressTmService.findByProgressNo(req.getProgressNo());
            log.info("existingProgress"+existingProgress);

            if (existingProgress == null) {
                progressTmService.save(req);
            } else {
                existingProgress.setProgressNo(req.getProgressNo());
                existingProgress.setFinalTm(req.getFinalTm());
                existingProgress.setMaxTm(req.getMaxTm());
                progressTmService.saveTmData(existingProgress);
            }

            return ResponseEntity.ok("데이터가 성공적으로 저장 또는 업데이트되었습니다." + req);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
        }
    }
}
