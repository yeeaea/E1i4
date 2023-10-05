package org.online.lms.security.service;

import org.online.lms.security.domain.Members;
import org.online.lms.security.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MyUserDetailsService implements UserDetailsService {
    private final MemberService memberService;

    @Autowired
    public MyUserDetailsService(MemberService memberService) {
        this.memberService = memberService;
    }

    // DB에서 가져온 유저 정보를 시큐리티한테 넘겨주기
    @Override
    public UserDetails loadUserByUsername(String insertedId) throws UsernameNotFoundException {
        Optional<Members> findOne = memberService.findByLoginId(insertedId);
        Members member = findOne.orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다."));

        return User.builder()
                .username(member.getMemberName())
                .password(member.getLoginPw())
                .build();
    }
}
