package org.online.lms.security.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.aspectj.bridge.Message;
import org.online.lms.security.domain.Members;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude="loginPw")    // 해당 클래스 객체 출력 시, 비밀번호 필드 출력되지 않도록 설정
public class MemberSignupDTO {


    // 회원가입 로직 ( 유효성 검사 포함 )
    /*
    *  < 유효성 검사에 사용된 어노테이션 총 정리 >
    *   @NotBlank : 해당 필드가 공백이 아니어야 함
    *    - message 속성 : 유효성 검사 실패 시, 사용자에게 보여줄 오류 메시지 지정
    *   @Size : 해당 필드의 길이가 지정한 범위 내에 있어야 함 ( min, max )
    *    - message 속성 : 위와 동일
    *   @Pattern : 정규 표현식 패턴과 일치하는지 여부 확인
    *    - regexp 속성 : 정규식 표현식 패턴을 문자열로 지정
    *    - 정규식 표현식 규칙 정리
    *      .* : 문자열 내의 모든 내용을 나타냄
    *      (?=.*[0-9]) : 적어도 하나의 숫자(0~9)가 포함되어야 함
    *      (?=.*[a-zA-Z]) : 적어도 하나의 영문 대소문자 알파벳이 포함되어야 함
    *      (?=.*\\W) : 적어도 하나의 특수문자(특수기호 또는 기타 비알파벳 문자)가 포함되어야 함
    *      (?=\\S+$) : 문자열은 공백을 포함하지 않아야 함
    *      .{8,16} : 문자열의 총 길이는 8에서 16자 사이여야 함
    *   @Email : 해당 필드의 값이 이메일 주소의 형식을 따르는지 여부 확인
    *            -> 적어도 하나의 '@'를 포함해야 함
    * */
    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 6, max = 15, message = "아이디는 6~15자 내에서 입력해주세요.")
    private String loginId; // 로그인 id

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
             message="비밀번호는 8~16자 내에서 숫자, 특수문자, 영문 대소문자를 반드시 포함해야 합니다.")
    private String loginPw; // 비밀번호

    @NotBlank(message = "이름을 입력해주세요.")
    private String memberName; // 이름

    // 필수 입력 항목 아님 -> @NotBlank 생략!
    private String memberTel; // 전화번호

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 형식으로 입력해주세요.")
    private String memberEmail; // 이메일


    // DTO -> Entity로 변환
    public Members toEntity(){
        return Members.builder()
                .loginId(loginId)
                .loginPw(loginPw)
                .memberName(memberName)
                .memberTel(memberTel)
                .memberEmail(memberEmail)
                .build();
    }
}
