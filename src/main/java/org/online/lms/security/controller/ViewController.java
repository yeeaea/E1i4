package org.online.lms.security.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.online.lms.security.dto.MemberSignupDTO;
import org.online.lms.security.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ViewController {
    private final MemberService memberService;

    @GetMapping("/")    // 메인 페이지로 이동
    public String login(){  // 커스텀 로그인 페이지 지정
        return "login";
    }

    @GetMapping("/lms/mypage")    // 마이페이지로 이동
    public String mypage(){
        return "/page/content";
    }

    @GetMapping("/lms/signup")      // 회원가입 페이지로 이동
    public String signUp(){
        return "signUp";
    }

    @PostMapping("/lms/signup")        // 회원가입 처리
    public String processSignup(@Valid MemberSignupDTO dto, Errors errors, Model model){
        // @Valid가 적용된 MemberSignupDTO 객체 유효성 검증 -> 실패 시, 에러 정보가 Errors 객체에 저장됨
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
        return "findId";
    }

    @GetMapping("/lms/find-pw")     // 비밀번호 찾기 페이지로 이동
    public String findPw(){
        return "findPw";
    }
}
