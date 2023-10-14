package org.online.lms.security.config;

import jakarta.servlet.DispatcherType;
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
                        .requestMatchers("/**").permitAll() // /** : 전체 페이지 접근 허용
                        .requestMatchers("/admin/**").hasAuthority(MemberRole.ADMIN.getRoleName())
                        //.anyRequest().permitAll()       // 어떠한 요청이라도 모두 허용
                        .anyRequest().authenticated() // 어떠한 요청이라도 인증 필요
                )
                .formLogin(login -> login
                        .loginPage("/page/security/login")
                        .loginProcessingUrl("/login-process")
                        .usernameParameter("loginId")
                        .passwordParameter("loginPw")
                        .successHandler((request, response, authentication) -> {
                            // 여기에서 authentication을 이용해 역할을 확인하고 적절한 URL로 리다이렉트
                            if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals(MemberRole.ADMIN.getRoleName()))) {
                                log.info("스프링 설정 파일에서 권한 확인 : " + MemberRole.ADMIN.getRoleName());
                                response.sendRedirect("/admin/mypage");
                            } else {
                                response.sendRedirect("/lms/mypage");
                            }
                        })
                        .permitAll()
                )
                .logout(withDefaults());

        return http.build();
    }
}