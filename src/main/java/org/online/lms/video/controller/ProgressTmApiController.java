package org.online.lms.video.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.online.lms.lecture.repository.LectureApplyRepository;
import org.online.lms.lecture.service.LectureApplyService;
import org.online.lms.security.service.MemberService;
import org.online.lms.video.domain.Progress;
import org.online.lms.video.domain.ProgressInfo;
import org.online.lms.video.dto.ProgressTmRequest;
import org.online.lms.video.service.ProgressInfoService;
import org.online.lms.video.service.ProgressTmService;
import org.online.lms.video.service.VideoInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

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



    private Map<String, Progress> progressMap = new HashMap<>();

    @PostMapping("/api/admin/saveYoutubeTm")
    public Progress saveOrUpdateProgress(@RequestBody ProgressTmRequest req,
                                         HttpSession session, Principal principal,
                                         Model model) {

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
                progress.setNthNo(req.getNthNo());
                progress.setFinalTm(req.getFinalTm());
                progress.setMaxTm(req.getMaxTm());
                progressMap.put(req.getNthNo().toString(), progress);
               return progressTmService.saveTmData(progress);
            }

            return progress;
        }
        return null;
    }
//////////////////////////////////////////////////////////////
    // 이거를 사용!!!!!!!!
//    @PostMapping("/api/admin/saveYoutubeTm")
//    public Progress saveOrUpdateProgress(
//            @RequestBody ProgressTmRequest req,
//            HttpSession session, Principal principal) {
//
//        String sessionID = session.getId();
//        log.info("세션 아이디" + sessionID);
//
//        if (principal != null) {
//            // 로그인 아이디 받아오기
//            String loginId = principal.getName();
//
//
//            System.out.println("=======" + req.getNthNo());  // 나옴 - 이거를 통해서 if문?
//            Progress newProgress = Progress.progressTm();
//            //newProgress.setProgressNo(9L);
//            newProgress.setNthNo(req.getNthNo());  // 반환값 객체로 해보고 아니면 테이블 Long 변환했음^^
//            newProgress.setMemberNo(loginId);
//            newProgress.setFinalTm(req.getFinalTm());
//            newProgress.setMaxTm(req.getMaxTm());
//            return progressTmService.saveTmData(newProgress);
//        }
//        return null;
//    }
////////////////////////////////////////////////////
//    @PostMapping("/api/admin/saveYoutubeTm")
//    public Progress updateProgress(
//             Long progressNo,
//            HttpSession session,
//            @RequestBody ProgressTmRequest req
//    ) {
//
//        // ProgressService의 progressNoSave 메서드를 호출하여 처리
//        Progress progress = progressTmService.progressNoSave(progressNo, session, req);
//
//        if (progress != null) {
//            return progress;
//        } else {
//            // 해당 progressNo에 해당하는 Progress가 없는 경우에 대한 처리
//            // 여기에서는 null을 반환했으므로 클라이언트는 이 상황을 처리해야합니다.
//            // 예를 들어, 404 Not Found 또는 다른 오류 응답을 보낼 수 있습니다.
//            return null;
//        }
//    }
}

//    public Progress saveOrUpdateProgress(
//            @RequestBody ProgressTmRequest req,
//            Principal principal,
//            HttpSession session,
//            @RequestParam Long progressNo,
//            String finalTm,
//            String maxTm)


// loginId 저장하는 부분 참고!!!!!!
///////////////////////////////////////////////////
//        Optional<Progress> optionalProgress  = progressTmService.findByProgressNo(progressNo);
//        log.info(progressNo + "여기 초기화 해준 거 아님?");
//        // 세션에서 세션 id 가져오기
//        String sessionID = session.getId();
//        log.info("세션 아이디" + sessionID);
////
////        if (principal != null) {
//////            // 로그인 아이디 받아오기
//////            String loginId = principal.getName();
//
//if(optionalProgress.isPresent()) {
//    Progress progress = optionalProgress.get();
//    progress.setMemberNo(loginId);
//    progress.setFinalTm(finalTm);
//    progress.setMaxTm(maxTm);
//    return progressTmService.saveTmData(progress);
//
//////////////////////////////////////////////////////


//}else {
//    Progress newProgress = Progress.progressTm();
//    newProgress.setFinalTm(req.getFinalTm());
//    newProgress.setMaxTm(req.getMaxTm());
//    return progressTmService.saveTmData(newProgress);
//}
//
//
//
//
//            // 응답 값 확인
//            // {
//            //    "progressNo": 20,  // 이거를 통해서 계속 저장되고 그 값을 불러와야 되는 거
//            //    "nthNo": null, // 필수 값 (차시 번호가 곧 콘텐츠 번호)
//            //    "lectureNo": null,  // 필수 값 (강의 번호)
//            //    "memberNo": null,  // 로그인한 아이디에 대해 세션 정보를 받아서 저장할 거지만 이거도 넘겨주면 좋지
//            //    "startTm": null,  // finalTM값이 같이 저장되면 됨
//            //    "endTm": null,
//            //    "finalTm": "9.997053988555908",
//            //    "maxTm": "9.997053988555908",
//            //    "progRt": null
//            //}
//
//        }
////////////////////////////////////////////////////


        ////////////////////////////////// 참고할 렉쳐
        /*
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
        */
        ///////////////////////////////////
/*
 @PostMapping("/api/admin/saveYoutubeTm")
    public ResponseEntity<String> saveOrUpdateProgress(@RequestBody ProgressTmRequest req, Principal principal, Model model) {


        if (principal != null) {
            // 로그인 아이디 받아오기
            String loginId = principal.getName();

            // 로그인 아이디로 회원 번호 찾기
            Optional<Members> member =  memberService.findByLoginId(loginId);

            Long memberNo = member.get().getMemberNo();

            // 회원 번호로 수강 신청한 강의번호 찾기
            List<Long> lectureNos = lectureApplyService.findLectureNumbersByMemberNo(memberNo);
            log.info(lectureNos + "강의번호 찾아야지");

            // 강의번호에 연결된 콘텐츠 번호들 가져오기
            // 콘텐츠 테이블을 통해서 차시 가져오기
            List<Content> contents = new ArrayList<>();
            for(Long lecutreNo : lectureNos) {
                Content content = progressTmService.findContentByLectureNo(lecutreNo);
                log.info(content + "뭐가 나오나 강의 번호에 연결된 콘텐츠가 게 나오나?");
                contents.add(content);
            }

        }

//        // 회원 번호로 수강 신청한 강의번호 찾기
//        // 여기서 가져온 강의번호가
//        List<Long> lectureNos = lectureApplyService.findLectureNumbersByMemberNo(memberNo);
//        log.info(lectureNos + "강의번호 찾아야지");
//
//        List<LectureApply> lectureApplies = lectureApplyRepository.findByLectureNoIn(lectureNos);
//        for (LectureApply lectureApply : lectureApplies) {
//            //LectureInfo lecture = lectureApply.getLecture(); // 연관된 Lecture 정보 가져오기
//        //log.info(lecture + "수강신청한 강의목록 가져오기!");
//            // 이제 Lecture 엔티티의 필드 정보를 사용할 수 있습니다
//            // 예: String lectureName = lecture.getLectureName();
//        }
//
//        List<ProgressInfo> progressInfos = progressInfoService.findAll();
//        for(ProgressInfo progressInfo : progressInfos) {
//            // 여기서 가져온 강의 번호와 같은 경우 for문으로 콘텐츠 목록을 돌아가면서 전체 리스트 뽑고
//            // 그 중에서 연결된 차시를 뽑아서 가져오는 거야 (아니 그러면 nth_no 가져오면 되잖아)
//            // 그래 다시 생각해보자.. nth_no를 가져올 건데,, 거기 있는 강의번호가 수강신청한 강의번호와 같은 경우
//            ///// 내가 필요한 거 : 통제를 위한 콘텐츠 영상 1개
//            ///// 통제 : 수강자 당 영상 통제 기록 1개 (progressNo에 저장)
//            // 사용자가 듣는 강의가 필요하므로 수강신청한 강의들을 가져와야하고
//            // 그 신청한 강의 목록 들 중에서 1개의 강의를 가져오기 -> 이거는 지금 현재 재생하는 강의에 대한 정보
//            // 수강신청한 강의 번호 lectureNos(재생중인 강의번호) == lectureByPi 차시 테이블의 강의 번호
//            // -> 콘텐츠 번호를 가져오기 contentNo
//            // 수강신청한 강의와 콘텐츠가 연결된 차시 테이블의 기본키?
//            LectureInfo lectureByPi = progressInfo.getLecture();
//            Long lectureNo = lectureByPi.getLectureNo();
//            log.info(lectureNo + " : 차시 테이블의 강의 읽어오기");
//
//            Content contentByPi = progressInfo.getContent();
//            Long contentNo = contentByPi.getContentNo();
//            log.info(contentNo + " : 차시 테이블의 콘텐츠를 읽어오기");
//
//            // 차시 테이블의 구조를 봤을 때,
//            // 강의와 차시는 1대1 -> 수강신청한 강의번호를 가져오기
//            // 강의와 콘텐츠는 1대다 => 차시를 통해서 강의번호를 가져오고 그 강의번호와 위에 가져온 강의 번호가
//            // 일치하는 경우, 콘텐츠 번호를 가져오기(콘텐츠는 리스트로 .get()해서 가져오기)
//        }
//
//        // 콘텐츠 테이블을 통해서 차시 가져오기
//        List<Content> contents = videoInfoService.findAll();
//        for(Content content : contents) {
//            // 이거는 연결 컬럼이 안 되어있어서 Progressinfo에서 못뽑음
//            // Long으로 그냥 하나 뽑아야 되는 거...
//            Long progressInfo = content.getContentNo();
//        }


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
*/