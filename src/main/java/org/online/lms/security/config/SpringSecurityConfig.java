package org.online.lms.security.config;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.online.lms.security.domain.role.MemberRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
@Slf4j
public class SpringSecurityConfig {
    @Bean
    PasswordEncoder passwordEncoder() {
        // BCryptPasswordEncoder : 비밀번호 암호화 후, 서버에 저장
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable()).cors((cors) -> cors.disable())
                .authorizeHttpRequests(request -> request
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers("/**").permitAll() // 나중에 주석처리!!!!
                        //.requestMatchers("/", "/css/**", "/files/**", "/img/**", "/js/**", "/lms/signup"
                        //        ,"/lms/find-id","/lms/find-pw", "/lms/api/members/current-memberName").permitAll()
                        //.requestMatchers("/admin/**").hasAuthority(MemberRole.ADMIN.getRoleName())
                        .anyRequest().permitAll()       // 어떠한 요청이라도 모두 허용
                        //.anyRequest().authenticated() // 어떠한 요청이라도 인증 필요
                )
                .formLogin(login -> login
                        .loginPage("/page/security/login")
                        .loginProcessingUrl("/login-process")
                        .usernameParameter("loginId")
                        .passwordParameter("loginPw")
                        .successHandler(new AuthenticationSuccessHandler() {
                            @Override
                            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                                // 여기에서 authentication을 이용해 역할을 확인하고 적절한 URL로 리다이렉트
                                //System.out.println(MemberRole.ADMIN.getRoleName());     // ADMIN
                                System.out.println(authentication.getAuthorities());      // *** 문제 : authentication에서 못 가져옴 -> []
                                log.info("로그인 분기처리 전");
                                if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals(MemberRole.ADMIN.getRoleName()))) {
                                    response.sendRedirect("/admin/memberInfo");
                                } else {
                                    response.sendRedirect("/lms/mypage");
                                }
                            }
                        })
                        .permitAll()
                )
                .logout((logout) -> logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/")      // 로그아웃 시, 로그인 페이지(메인)로 이동
                        .permitAll());

        return http.build();
    }
}