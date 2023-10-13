package org.online.lms.security.config;

import jakarta.servlet.DispatcherType;
import lombok.extern.slf4j.Slf4j;
import org.online.lms.security.handler.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.online.lms.security.handler.CustomAuthenticationSuccessHandler;
import static org.springframework.security.config.Customizer.withDefaults;


@Slf4j
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
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
                        //.requestMatchers("/admin/**").hasAuthority(MemberRole.ADMIN.getRoleName())
                        //.anyRequest().permitAll()       // 어떠한 요청이라도 모두 허용
                        .anyRequest().authenticated() // 어떠한 요청이라도 인증 필요
                )
                .formLogin(login -> login
                        .loginPage("/page/security/login")      // 커스텀 로그인 페이지 지정
                        .loginProcessingUrl("/login-process")   // submit 받을 url
                        .usernameParameter("loginId")           // submit할 아이디
                        .passwordParameter("loginPw")           // submit할 비밀번호
                        //.defaultSuccessUrl("/lms/mypage", true)
                        .successHandler(authenticationSuccessHandler()) // 로그인 성공 시 핸들러 작동
                        .permitAll()
                )
                .logout(withDefaults());

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }
}