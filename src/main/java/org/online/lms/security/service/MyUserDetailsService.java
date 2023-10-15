package org.online.lms.security.service;

import org.online.lms.security.domain.Members;
import org.online.lms.security.domain.role.MemberRole;
import org.online.lms.security.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
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

        // enum 클래스에서 사용자의 권한 정보를 가져옴
        MemberRole memberRole = member.getMemberRole();

        // memberRole 변수에서 가져온 권한 정보를 Spring Security의 GrantedAuthority 객체로 변환
        // SimpleGrantedAuthority : 권한을 나타내는 Spring Security의 기본 구현체 중 하나
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(memberRole.getRoleName()));

        return User.builder()
                .username(member.getLoginId())
                .password(member.getLoginPw())
                // authorities : 사용자의 권한을 설정
                .authorities(authorities)
                .build();
    }
}
