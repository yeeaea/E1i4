package org.online.lms.security.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.online.lms.security.domain.Members;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMemberInfoDTO {
    private String loginId;     // 로그인 id
    private String loginPw;     // 비밀번호
    private String memberName;  // 이름
    private String memberTel;   // 전화번호
    private String memberEmail; // 이메일


}
