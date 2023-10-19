package org.online.lms.security.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.online.lms.lecture.domain.LectureInfo;
import org.online.lms.lecture.dto.MyLectureInfoDto;
import org.online.lms.lecture.service.LectureApplyService;
import org.online.lms.lecture.service.LectureInfoService;
import org.online.lms.security.domain.Members;
import org.online.lms.security.dto.MemberPwChangeDTO;
import org.online.lms.security.dto.MemberSignupDTO;
import org.online.lms.security.dto.UpdateMemberInfoDTO;
import org.online.lms.security.service.MemberService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberViewController {
    private final MemberService memberService;
    private final LectureApplyService lectureApplyService;
    private final LectureInfoService lectureInfoService;

    // 메인 페이지로 이동
    @GetMapping("/")
    public String login(){  // 커스텀 로그인 페이지 지정
        return "/page/security/login";
    }

    // 마이 페이지로 이동
    @GetMapping("/lms/mypage")
    public String showMypage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String loginId = authentication.getName();
        // loginId를 사용하여 회원 정보를 데이터베이스에서 조회
        Optional<Members> userOptional = memberService.findByLoginId(loginId);

        Members member = userOptional.get();
        log.info("사용자 권한(컨트롤러에서 확인) : " + String.valueOf(member.getMemberRole()));
        String memberName = member.getMemberName();
        log.info("컨트롤러에서 서비스 진입 전");
        log.info("회원번호 : " + member.getMemberNo());

        // MemberService에서 데이터를 가져오기
        List<MyLectureInfoDto> myLectureList = memberService.findMyLectureInfoByMemberNo(member.getMemberNo());
        log.info("나의 수강 리스트 : " + myLectureList);

        log.info("나의 수강 리스트 : " + myLectureList.toString());
        model.addAttribute("loginId", loginId);
        model.addAttribute("memberName", memberName);
        model.addAttribute("myLectureList", myLectureList);

        return "/page/security/mypage";
    }

    // 회원가입 페이지로 이동
    @GetMapping("/lms/signup")
    public String signUp(){
        return "/page/security/signup";
    }

    // 회원가입 처리
    @PostMapping("/lms/signup")
    public String processSignup(@Valid MemberSignupDTO dto, Errors errors, Model model){
        // @Valid가 적용된 MemberSignupDTO 객체 유효성 검증 -> 실패 시, 에러 정보가 Errors 객체에 저장됨
        log.info("dto : {}, errors : {}", dto, errors);
        if(errors.hasErrors()){
            log.info("회원가입 실패");
            // 회원가입 실패 시, 입력 데이터 값 그대로 유지
            model.addAttribute("signupDTO", dto);
            // 유효성 검증 실패한 필드와 메시지 핸들링
            Map<String, String> validatorResult = memberService.validateHandling(errors);
            // validatorResult.keySet() : Map의 key를 가진 Set 객체 반환
            // ( Set : Map의 키를 담아두는 컬렉션 )
            for(String key : validatorResult.keySet()){
                // 각 key에 해당하는 값을 model에 추가 -> 뷰에서 오류 메시지 표시 목적
                model.addAttribute(key, validatorResult.get(key));
            }
            // 회원가입 페이지로 다시 이동
            return "/page/security/signup";
        }
        memberService.registerMember(dto);
        // 회원가입이 완료된 이후에 로그인 페이지로 이동
        return "redirect:/?success=true";
    }

    // 아이디 찾기 페이지로 이동
    @GetMapping("/lms/find-id")
    public String findId(){
        return "/page/security/findId";
    }

    // 비밀번호 찾기 페이지로 이동
    @GetMapping("/lms/find-pw")
    public String findPw(){
        return "/page/security/findPw";
    }

    // 뷰에서 사용자가 입력한 memberEmail를 파라미터로 받아 이메일 유무 확인하는 용도
    @PostMapping("/lms/find-pw/check")
    @ResponseBody
    public boolean checkEmail(@RequestParam(name = "memberEmail") String memberEmail){
        log.info("checkEmail 진입");
        // 아이디 조회 폼을 보여주는 뷰 이름
        return memberService.checkEmail(memberEmail);
    }

    // 임시 비밀번호 생성 후, 전송
    @PostMapping("/lms/find-pw/send")
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

    // 비밀번호 변경하는 페이지 이동
    @GetMapping("/lms/mypage/change-pw")
    public String showChangePw(){
        return "/page/security/changePw";
    }

    // 비밀번호 변경 처리
    @PostMapping("/lms/mypage/change-pw")
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
        log.info("사용자의 DB에 저장되어 있는 비밀번호 : " + dto.getCurrentPw());
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

    // 개인정보 수정 페이지로 이동
    @PreAuthorize("isAuthenticated()")  // 로그인된 사용자만 이 페이지 볼 수 있음
    @GetMapping("/lms/mypage/edit-info")
    public String showEditInfoPage(Model model, Authentication authentication, @AuthenticationPrincipal Members member){

        // 현재 로그인된 사용자인 경우
        if(authentication != null && authentication.isAuthenticated()){
            // 로그인된 아이디 가져옴
            String loginId = authentication.getName();
            // 위에서 가져온 아이디를 기준으로 사용자 정보 불러옴
            Optional<Members> currentMember = memberService.findByLoginId(loginId);
            if(currentMember.isPresent()){
                // model에 member 객체 추가해서 뷰로 넘겨줌
                model.addAttribute("member", currentMember.get());
                return "/page/security/memberInfo";
            } else {
                return "/page/error";
            }
        } else {
            // 로그인되지 않은 경우 처리
            return "/page/security/login";
        }
    }

    // 개인정보 수정 페이지 처리
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/lms/mypage/edit-info")
    public String processInfoPage(UpdateMemberInfoDTO dto, Errors errors, Model model, @AuthenticationPrincipal Members member){
        // 유효성 검사 아직 진행하진 못했지만, 유효성 검사 통과 시 아래 코드 실행
        model.addAttribute("member", dto);
        Members updatedMember = memberService.updateMemberInfo(dto);

        return "redirect:/lms/mypage/edit-info";  // 정보 업데이트시킨 후, 마이페이지로 이동

    }

}