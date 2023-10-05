package org.online.lms.security.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "admin")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin { // 관리자 정보 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_no", updatable = false)
    private Long adminNo; // 관리자 번호

    @Column(name = "login_id", nullable = false)
    private String loginId; // 로그인 id

    @Column(name = "login_pw", nullable = false)
    private String loginPw; // 비밀번호

    @Column(name = "admin_name", nullable = false)
    private String adminName; // 이름

    @Column(name = "admin_tel")
    private String adminTel; // 전화번호

    @Column(name = "admin_email", nullable = false)
    private String adminEmail; // 이메일

    @Column(name = "admin_permission", nullable = false)
    private String adminPermission; // 관리자 권한

    @Builder
    public Admin(String loginId,
                 String loginPw,
                 String adminName,
                 String adminTel,
                 String adminEmail,
                 String adminPermission) {
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.adminName = adminName;
        this.adminTel = adminTel;
        this.adminEmail = adminEmail;
        this.adminPermission = adminPermission;
    }
}
