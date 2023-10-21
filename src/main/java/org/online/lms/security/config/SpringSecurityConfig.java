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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
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
                        //.requestMatchers("/**").permitAll() // 나중에 주석처리!!!!
                        .requestMatchers("/", "/css/**", "/files/**", "/img/**", "/js/**", "/lms/signup"
                                ,"/lms/find-id","/lms/find-pw", "/lms/api/members/current-memberName").permitAll()
                        .requestMatchers("/lms/**").hasAuthority(MemberRole.USER.getRoleName())     // 사용자 권한 페이지
                        .requestMatchers("/admin/**").hasAuthority(MemberRole.ADMIN.getRoleName())  // 관리자 권한 페이지
                        //.anyRequest().permitAll()       // 어떠한 요청이라도 모두 허용
                        .anyRequest().authenticated() // 어떠한 요청이라도 인증 필요
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
        http.exceptionHandling(handler -> handler.authenticationEntryPoint(new AuthenticationEntryPoint() {

                    @Override
                    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) throws IOException, ServletException {
                        // 인증되지 않은 사용자가 페이지에 올 경우
                        // 로그인 하시면 way의 더 많은 기능을 사용하실 수 있습니다!
                        res.sendRedirect("/error");
                    }
                })
                .accessDeniedHandler(new AccessDeniedHandler() {

                    @Override
                    public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException accessDeniedException) throws IOException, ServletException {

                        // 유저가 관리자의 권한 페이지를 사용하려고 하는 경우
                        // 관리자만 권한이 있는 페이지입니다!
                        res.sendRedirect("/error");
                    }
                }));

        return http.build();
    }
}