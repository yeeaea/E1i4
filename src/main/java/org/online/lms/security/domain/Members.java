package org.online.lms.security.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Members { // 회원 정보 테이블
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_no", updatable = false)
    private long memberNo; // 회원 번호

    @Column(name = "login_id", nullable = false)
    private String loginId; // 로그인 id

    @Column(name = "login_pw", nullable = false)
    private String loginPw; // 비밀번호

    @Column(name = "member_name", nullable = false)
    private String memberName; // 이름

    @Column(name = "member_tel")
    private String memberTel; // 전화번호

    @Column(name = "member_email", nullable = false)
    private String memberEmail; // 이메일

    @Builder
    public Members(String loginId,
                   String loginPw,
                   String memberName,
                   String memberTel,
                   String memberEmail) {
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.memberName = memberName;
        this.memberTel = memberTel;
        this.memberEmail = memberEmail;
    }

}
