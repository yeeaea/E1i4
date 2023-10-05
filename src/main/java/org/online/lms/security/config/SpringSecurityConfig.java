package org.online.lms.security.config;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
public class SpringSecurityConfig {
    @Bean
    PasswordEncoder passwordEncoder() {
        // BCryptPasswordEncoder : 비밀번호 암호화 후, 서버에 저장
        return new SimplePasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable()).cors((cors) -> cors.disable())
                .authorizeHttpRequests(request -> request
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers("/**").permitAll() // /** : 전체 페이지 접근 허용
                        //.anyRequest().permitAll()       // 어떠한 요청이라도 모두 허용
                        .anyRequest().authenticated() // 어떠한 요청이라도 인증 필요
                )
                .formLogin(login -> login
                        .loginPage("/login")                    // 커스텀 로그인 페이지 지정
                        .loginProcessingUrl("/login-process")   // submit받을 URL
                        .usernameParameter("loginId")           // submit할 아이디
                        .passwordParameter("loginPw")           // submit할 비밀번호
                        .defaultSuccessUrl("/lms/mypage", true) // 로그인 성공 -> 회원 마이페이지로 이동
                        .permitAll()
                )
                .logout(withDefaults());

        return http.build();
    }
}
