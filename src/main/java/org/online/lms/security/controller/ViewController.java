package org.online.lms.security.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.online.lms.security.domain.Members;
import org.online.lms.security.dto.MemberPwChangeDTO;
import org.online.lms.security.dto.MemberSignupDTO;
import org.online.lms.security.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.core.Authentication;

import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ViewController {
    private final MemberService memberService;

    @GetMapping("/")    // 메인 페이지로 이동
    public String login(){  // 커스텀 로그인 페이지 지정
        return "/page/security/login";
    }

    @GetMapping("/lms/mypage")    // 마이페이지로 이동
    public String mypage(){
        return "/page/security/mypage";
    }

    @GetMapping("/lms/signup")      // 회원가입 페이지로 이동
    public String signUp(){
        return "/page/security/signUp";
    }

    @PostMapping("/lms/signup")        // 회원가입 처리
    public String processSignup(@Valid MemberSignupDTO dto, Errors errors, Model model){
//        // @Valid가 적용된 MemberSignupDTO 객체 유효성 검증 -> 실패 시, 에러 정보가 Errors 객체에 저장됨
//        log.info("dto : {}, errors : {}", dto, errors);
//        if(errors.hasErrors()){
//            // 회원가입 실패 시, 입력 데이터 값 그대로 유지
//            model.addAttribute("signupDTO", dto);
//            // 유효성 검증 실패한 필드와 메시지 핸들링
//            Map<String, String> validatorResult = memberService.validateHandling(errors);
//            // validatorResult.keySet() : Map의 key를 가진 Set 객체 반환
//            // ( Set : Map의 키를 담아두는 컬렉션 )
//            for(String key : validatorResult.keySet()){
//                // 각 key에 해당하는 값을 model에 추가 -> 뷰에서 오류 메시지 표시 목적
//                model.addAttribute(key, validatorResult.get(key));
//            }
//            // 회원가입 페이지로 다시 이동
//            return "/lms/signup";
//        }
        memberService.registerMember(dto);
        // 회원가입이 완료된 이후에 로그인 페이지로 이동
        return "redirect:/?success=true";
    }

    @GetMapping("/signup/checkId")      // 회원가입 시, 아이디 중복체크 ( Ajax 요청 포함 )
    @ResponseBody
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


    @GetMapping("/lms/find-id")     // 아이디 찾기 페이지로 이동
    public String findId(){
        return "/page/security/findId";
    }

    @PostMapping("/lms/find-id")
    @ResponseBody
    public ResponseEntity<String> processFindId(@RequestParam(name = "memberName") String memberName, @RequestParam(name = "memberEmail") String memberEmail, Model model){
        String findId = memberService.searchByLoginId(memberName, memberEmail);

        if(findId != null){
            return ResponseEntity.ok(findId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("입력하신 정보와 일치하는 아이디가 없습니다.");
        }
    }

    @GetMapping("/lms/find-pw")     // 비밀번호 찾기 페이지로 이동
    public String findPw(){
        return "/page/security/findPw";
    }

    @PostMapping("/lms/find-pw/check")  // 뷰에서 사용자가 입력한 memberEmail를 파라미터로 받아 이메일 유무 확인하는 용도
    @ResponseBody
    public boolean checkEmail(@RequestParam(name = "memberEmail") String memberEmail){
        log.info("checkEmail 진입");
        // 아이디 조회 폼을 보여주는 뷰 이름
        return memberService.checkEmail(memberEmail);
    }
    @PostMapping("/lms/find-pw/send") // 임시 비밀번호 생성 후, 전송
    @ResponseBody
    public String sendEmail(@RequestParam(name = "memberEmail") String memberEmail){
        log.info("sendEmail 집입");
        log.info("이메일 : " + memberEmail);

        // 임시 비밀번호 생성
        String tmpPwd = memberService.getTmpPwd();

        // 임시 비밀번호 저장
        memberService.updatePwd(tmpPwd, memberEmail);

        log.info("임시 비밀번호 : " + tmpPwd);
        log.info("임시 비밀번호 전송 완료");

        return "" + tmpPwd;
    }

    @GetMapping("/lms/mypage/change-pw")     // 비밀번호 변경하는 페이지 이동
    public String showChangePw(){
        return "/page/security/changePw";
    }

    @PostMapping("/lms/mypage/change-pw")   // 비밀번호 변경 처리
    /*
    *  Authentication : 현재 사용자의 인증 정보를 포함한 객체
    *  @AuthenticationPrincipal : 현재 사용자의 정보를 나타내는 어노테이션으로,
    *                              Spring Security를 사용해 현재 사용자 정보 가져옴
    * */
    public String updatePw(@Valid MemberPwChangeDTO dto, Errors errors, Model model,
                           Authentication authentication, @AuthenticationPrincipal Members member){
        // 현재 사용자 인증 정보에서 Principal를 가져와 UserDetails로 업캐스팅
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // 비밀번호 변경을 위해 memberService 호출 -> dto와 현재 사용자의 아이디를 전달
        String result = memberService.updateMemberPw(dto, userDetails.getUsername());

        // ① 현재 비밀번호가 틀렸을 경우
        if (result == null) {
            model.addAttribute("dto", dto);
            model.addAttribute("wrongPassword", "입력하신 비밀번호와 저장된 비밀번호가 일치하지 않습니다.");

            return "/page/security/changePw";   // 오류 메시지를 보여줄 HTML 페이지로 리다이렉트
        }
        // ② 새로 입력한 비밀번호와 비교
        if (!Objects.equals(dto.getNewPw(), dto.getConfirmPw())) {
            model.addAttribute("dto", dto);
            model.addAttribute("differentPassword", "새로 입력하신 비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
            return "/page/security/changePw";   // 오류 메시지를 보여줄 HTML 페이지로 리다이렉트
        }

        // 성공하면 로그아웃 -> 로그인 페이지로 이동
        return "redirect:/";
    }

    @GetMapping("/lms/mypage/edit-info")    // 개인정보 수정 페이지로 이동
    public String showEditInfoPage(){
        return "page/security/memberUpdate";
    }
}