package org.online.lms.security.controller;

import org.online.lms.security.domain.Members;
import org.online.lms.security.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/lms/api/members")
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


}
