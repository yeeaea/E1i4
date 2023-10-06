package org.online.lms.security.service;


import lombok.extern.slf4j.Slf4j;
import org.online.lms.security.domain.Members;
import org.online.lms.security.dto.MemberSignupDTO;
import org.online.lms.security.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired  // 생성자 의존성 주입
    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder){
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // * 회원가입 로직 ( 유효성 검사 포함 )
    // 1) Member 엔티티 저장
    public Members saveMember(Members members){
        return memberRepository.save(members);
    }

    // 2) 비밀번호 암호화 후, DTO에서 받아온 데이터를 기반으로 회원정보 생성하고 저장
    public String registerMember(MemberSignupDTO dto) {
        // 회원가입 시, 입력받은 평문 비밀번호
        String rawPassword = dto.getLoginPw();
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(rawPassword);
        // DTO에서 받아온 데이터로 회원정보 생성
        Members member = Members.builder()
                            .loginId(dto.getLoginId())
                            .loginPw(encodedPassword)
                            .memberName(dto.getMemberName())
                            .memberTel(dto.getMemberTel())
                            .memberEmail(dto.getMemberEmail())
                            .build();
        // 회원정보 저장 후, 사용자의 Id값 반환 ( 새로 등록된 사용자의 ID 확인 목적 )
        return memberRepository.save(member).getLoginId();
    }

    // 3) 유효성 검증 에러 처리
    //  - Errors : 스프링 프레임워크가 제공하는 유효성 검증 결과를 담고 있는 객체
    //  - FieldError : 객체를 순회하면서 각 필드에 대한 유효성 검증 오류 처리
    //                 -> 필드이름을 키로, 오류 메시지를 값으로 가지는 Map 생성
    public Map<String, String> validateHandling(Errors errors){
        Map<String, String> validatorResult = new HashMap<>();

        // errors 객체의 getFieldErrors() 메서드 이용 -> 발생한 필드 오류 가져옴
        // 메서드의 반환값 : FieldError 객체의 리스트 -> 반복문 이용
        for(FieldError error : errors.getFieldErrors()){
            // FieldError 객체의 getField() 메서드 이용 -> 유효성 검증에 실패한 필드 이름 가져옴
            // String.format 메서드 이용 -> "valid_필드이름" 형식의 문자열 생성
            String validKeyName = String.format("valid_%s", error.getField());
            // FieldError 객체의 getDefaultMessage() 메서드 이용 -> 유효성 검증 실패 시 표시될 오류 메시지
            // validKeyName을 키로, 오류메시지를 값으로 사용하여 map에 저장
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        log.info("유효성 검증 에러 : " + validatorResult);
        // 유효성 검증 결과를 클라이언트에게 전달
        return validatorResult;
    }

    // * 아이디 중복 체크
    public boolean isLoginIdDuplicate(String loginId){
        Optional<Members> member = memberRepository.findByLoginId(loginId);
        return member.isPresent();
    }

    // * 아이디 검색
    //   -> 아이디로 회원 정보 찾기에 이용
    public Optional<Members> findByLoginId(String loginId){
        return memberRepository.findByLoginId(loginId);
    }


}
