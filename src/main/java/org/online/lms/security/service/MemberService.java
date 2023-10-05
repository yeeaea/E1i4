package org.online.lms.security.service;

import org.online.lms.security.domain.Members;
import org.online.lms.security.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    // * 아이디 검색
    //   -> 아이디로 회원 정보 찾기에 이용
    public Optional<Members> findByLoginId(String loginId){
        return memberRepository.findByLoginId(loginId);
    }
}
