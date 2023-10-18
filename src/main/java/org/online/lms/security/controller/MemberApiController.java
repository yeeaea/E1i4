package org.online.lms.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.online.lms.security.domain.Members;
import org.online.lms.security.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/lms/api/members")
@Slf4j
public class MemberApiController {
    private final MemberService memberService;
    @Autowired
    public MemberApiController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 회원 번호 받아오기
    @GetMapping("/current-memberNo")
    public ResponseEntity<Object> getCurrentMemberNo(Principal principal) {
        if (principal != null) {
            // Principal 객체를 사용하여 현대 로그인한 사용자의 아이디 가져오기
            String loginId = principal.getName();

            // loginId를 사용하여 회원번호를 데이터베이스에서 조회
            Optional<Members> userOptional = memberService.findByLoginId(loginId);
            if (userOptional.isPresent()) {
                Members member = userOptional.get();
                Long memberNo = member.getMemberNo();
                return ResponseEntity.ok().body("{\"success\": true, \"memberNo\": " + memberNo + "}");
            } else {
                // 사용자를 찾지 못한 경우에 대한 처리
                return ResponseEntity.ok().body("{\"success\": false, \"message\": \"사용자를 찾을 수 없습니다.\"}");
            }
        } else {
            // 인증되지 않은 사용자 처리
            return ResponseEntity.ok().body("{\"success\": false, \"message\": \"로그인 되지 않았습니다.\"}");
        }
    }

    // 회원 이름 가져오기
    @GetMapping("/current-memberName")
    public ResponseEntity<Map<String, String>> getCurrentMemberName() {
        Map<String, String> response = new HashMap<>();

        // 현재 사용자의 Authentication 객체 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String loginId = userDetails.getUsername();

            // loginId를 사용하여 회원 정보를 데이터베이스에서 조회
            Optional<Members> userOptional = memberService.findByLoginId(loginId);

            if (userOptional.isPresent()) {
                Members member = userOptional.get();
                String memberName = member.getMemberName();
                response.put("memberName", memberName);
            }
        } else {
            response.put("memberName", "Guest");
        }

        return ResponseEntity.ok(response);
    }

    // 회원가입 시, 아이디 중복체크 ( Ajax 요청 포함 )
    @GetMapping("/signup/checkId")
    public ResponseEntity<?> checkLoginId(@RequestParam(name = "loginId") String loginId){
        log.info("loginId : " + loginId);
        boolean isDuplicate = true;
        // 사용자가 입력한 아이디가 비어있지 않은 경우에만 중복 여부 확인
        if(!loginId.isEmpty()){
            isDuplicate = memberService.isLoginIdDuplicate(loginId);
        }
        // 중복 체크에 대한 조건문
        if(isDuplicate){
            return ResponseEntity.ok().body("다른 아이디를 입력하세요.");
        } else {
            return ResponseEntity.ok().body("사용 가능한 아이디입니다.");
        }
    }

    // 아이디 찾기 처리 ( Ajax 포함 )
    @PostMapping("/find-id")
    public ResponseEntity<String> processFindId(@RequestParam(name = "memberName") String memberName,
                                                @RequestParam(name = "memberEmail") String memberEmail, Model model){
        String findId = memberService.searchByLoginId(memberName, memberEmail);
        log.info("조회된 아이디 : " + findId);
        if(findId != null){
            return ResponseEntity.ok(findId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("입력하신 정보와 일치하는 아이디가 없습니다.");
        }
    }

    // 회원 아이디로 수강 신청한 강의 번호 리스트 받아오기
    @GetMapping("/lectureData")
    public ResponseEntity<List<String>> getLetureCourseByMemberNo() {
        log.info("MemberApiController 진입");
        // 현재 사용자의 Authentication 객체 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String loginId = userDetails.getUsername();

            if (userDetails != null) {
                List<String> lectureData = memberService.findLetureCourseByMemberLoginId(loginId);
                return ResponseEntity.ok(lectureData);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ArrayList<>());
    }

}
