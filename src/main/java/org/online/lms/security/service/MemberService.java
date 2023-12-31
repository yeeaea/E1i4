package org.online.lms.security.service;


import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.online.lms.lecture.dto.MyLectureInfoDto;
import org.online.lms.lecture.repository.LectureApplyRepository;
import org.online.lms.lecture.repository.LectureInfoRepository;
import org.online.lms.security.domain.Members;
import org.online.lms.security.dto.MemberPwChangeDTO;
import org.online.lms.security.dto.MemberSignupDTO;
import org.online.lms.security.dto.UpdateMemberInfoDTO;
import org.online.lms.security.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final LectureApplyRepository lectureApplyRepository;
    private final LectureInfoRepository lectureInfoRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired  // 생성자 의존성 주입
    public MemberService(MemberRepository memberRepository, LectureApplyRepository lectureApplyRepository, LectureInfoRepository lectureInfoRepository, PasswordEncoder passwordEncoder){
        this.memberRepository = memberRepository;
        this.lectureApplyRepository = lectureApplyRepository;
        this.lectureInfoRepository = lectureInfoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    ///////// * 회원가입 로직 ( 유효성 검사는 일단 나중에..! )
    // 1) Member 엔티티 저장
    public Members saveMember(Members member){
        return memberRepository.save(member);
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
    @Transactional
    public Map<String, String> validateHandling(Errors errors){
        Map<String, String> validatorResult = new HashMap<>();

        // errors 객체의 getFieldErrors() 메서드 이용 -> 발생한 필드 오류 가져옴
        // 위 메서드의 반환값 : FieldError 객체의 리스트 -> 반복문 이용
        for(FieldError error : errors.getFieldErrors()){
            // FieldError 객체의 getField() 메서드 이용 -> 유효성 검증에 실패한 필드 이름 가져옴
            // String.format 메서드 이용 -> "valid_필드이름" 형식의 문자열 생성
            String validKeyName = String.format("valid_%s", error.getField());
            // FieldError 객체의 getDefaultMessage() 메서드 이용 -> 유효성 검증 실패 시 표시될 오류 메시지 가져옴
            // validKeyName을 키로, 오류메시지를 값으로 사용하여 map에 저장
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        log.info("유효성 검증 에러 : " + validatorResult);
        // 유효성 검증 결과를 클라이언트에게 전달
        return validatorResult;
    }

    ///////// * 아이디 중복 체크
    public boolean isLoginIdDuplicate(String loginId){
        Optional<Members> member = memberRepository.findByLoginId(loginId);
        return member.isPresent();
    }

    ///////// * 아이디 검색
    //   -> 아이디로 회원 정보 찾기에 이용
    public Optional<Members> findByLoginId(String loginId){
        //log.info(loginId);
        return memberRepository.findByLoginId(loginId);
    }

    ///////// * 아이디 찾기
    public String searchByLoginId(String memberName, String memberEmail) {
        // 사용자 이름과 이메일 입력받기
        Optional<Members> member = memberRepository.findByMemberNameAndMemberEmail(memberName, memberEmail);

        if(member.isPresent()){
            String foundLoginId = member.get().getLoginId();
            return "찾으시는 아이디는 \"" + foundLoginId + "\" 입니다.";
        } else {
            return "입력하신 정보와 일치하는 아이디가 없습니다.";
        }
    }

    ///////// * 비밀번호 찾기
    // 1) DB에서 사용자의 이메일 유무 확인
    public boolean checkEmail(String memberEmail){
        log.info("사용자 이메일 조회 여부 : " + memberEmail);
        return memberRepository.findByMemberEmail(memberEmail).isPresent();
    }

    // 2) 임시 비밀번호 암호화 작업
    //   ① 임시 비밀번호 생성
    public String getTmpPwd() {
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String pwd = "";

        // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 조합
        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            pwd += charSet[idx];
        }
        log.info("임시 비밀번호 생성");

        return pwd;
    }
    //   ② 임시 비밀번호 업데이트
    // 임시 비밀번호로 업데이트
    public void updatePwd(String tmpPwd, String memberEmail) {
        log.info("임시 비밀번호 업데이트 시도 중 (0/4)");
        String encryptPassword = passwordEncoder.encode(tmpPwd);
        log.info("임시 비밀번호 업데이트 시도 중 (1/4)");
        Members member = memberRepository.findByMemberEmail(memberEmail).get();
        log.info("임시 비밀번호 업데이트 시도 중 (2/4)");
        // DB의 데이터 값을 변경
        member.setLoginPw(encryptPassword);
        log.info("임시 비밀번호 업데이트 시도 중 (3/4)");
        memberRepository.save(member);
        log.info("임시 비밀번호 업데이트 성공");
    }

    ///////// 비밀번호 변경 - 로그인된 아이디를 기준으로 비밀번호 입력 시, 맞으면 변경
    @Transactional
    public String updateMemberPw(MemberPwChangeDTO dto, String loginId) {
        // 변경을 위한 아이디 값 가져오기
        Members member = memberRepository.findByLoginId(loginId).get();
        // 아이디가 존재하지 않을 경우, 예외 발생
        if(member == null){
            new UsernameNotFoundException("사용자를 찾을 수 없습니다. 해당 아이디 : " + loginId);
        }
//        Members member = memberOptional.orElseThrow(()-> new UsernameNotFoundException("사용자를 찾을 수 없습니다. 해당 아이디 : " + loginId));
        log.info("비밀번호 변경할 아이디 : " + loginId);
        // DB에 저장된 비밀번호와 비교
        if( !passwordEncoder.matches(dto.getCurrentPw(), member.getLoginPw())){
            // 일치하지 않는 경우
            // 컨트롤러에서 null에 대한 에러 메시지 설정
            return null;
        } else {
            // 일치하는 경우
            // 새로 입력받은 비밀번호를 기존에 설정한 변수에 업데이트해주기
            member.setLoginPw(passwordEncoder.encode(dto.getNewPw()));
            memberRepository.save(member);
            return "업데이트";
        }
    }

    ///////// 회원정보 수정 ( 정보 업데이트 )
    @Transactional
    public Members updateMemberInfo(UpdateMemberInfoDTO dto) {
        // id로 사용자 확인
        Optional<Members> membersOptional = memberRepository.findByLoginId(dto.getLoginId());

        if(membersOptional.isPresent()){
            // 사용자가 있다면
            Members member = membersOptional.get();
            // 사용자 정보 변경
            member.setLoginId(dto.getLoginId());
            member.setMemberName(dto.getMemberName());
            member.setMemberTel(dto.getMemberTel());
            member.setMemberEmail(dto.getMemberEmail());
            // DB에 회원정보 저장
            return memberRepository.save(member);
        } else {
            // 사용자가 없다면
            return null;
        }
    }

    ///////// 회원 아이디로 수강 중인 강의 과정(Java, C) 가져오기 -> 구글 차트
    public List<String> findLetureCourseByMemberLoginId(String loginId){
        return lectureApplyRepository.findLectureCourseByLoginId(loginId);
    }

    // 회원번호로 강의과정, 강의제목, 강의 시작일, 강의 종료일, 수료여부 가져오기
    public List<MyLectureInfoDto> findMyLectureInfoByMemberNo(Long memberNo) {
        return lectureInfoRepository.findMyLectureInfoByMemberNo(memberNo);
    }
}