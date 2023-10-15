package org.online.lms.security.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.online.lms.security.domain.role.MemberRole;
import org.online.lms.security.dto.MemberSignupDTO;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "members")
@Getter
@Setter
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

    @Column(name = "member_attendance", columnDefinition = "boolean default false")
    private boolean memberAttendance; // 출결상태

    @Column(name = "member_role", nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'USER'")
    private MemberRole memberRole;   // 사용자 권한


    @Builder
    public Members(String loginId,
                   String loginPw,
                   String memberName,
                   String memberTel,
                   String memberEmail,
                   boolean memberAttendance,
                   MemberRole memberRole) {
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.memberName = memberName;
        this.memberTel = memberTel;
        this.memberEmail = memberEmail;
        this.memberAttendance = memberAttendance;
        this.memberRole = memberRole != null ? memberRole : MemberRole.USER;
    }

    // MemberSignDTO에서 받아온 정보로 Member 객체 생성
    public static Members createUser(MemberSignupDTO memberSignupDTO, PasswordEncoder passwordEncoder){
        Members member = Members.builder()
                .loginId(memberSignupDTO.getLoginId())
                .loginPw(passwordEncoder.encode(memberSignupDTO.getLoginPw()))
                .memberName(memberSignupDTO.getMemberName())
                .memberTel(memberSignupDTO.getMemberTel())
                .memberEmail(memberSignupDTO.getMemberEmail())
                .build();
        return member;
    }

}
