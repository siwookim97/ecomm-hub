package com.likelion.ecommhub.dto;

import com.likelion.ecommhub.domain.MemberRole;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MemberJoinDto {

    @NotEmpty(message = "회원 아이디는 공백이 불가합니다.")
    private String username;

    @NotEmpty(message = "회원 비밀번호는 공백이 불가합니다.")
    private String password;

    @NotEmpty(message = "회원 이름은 공백이 불가합니다.")
    private String nickname;

    @Email(message = "회원 이메일은 공백이 불가합니다.")
    private String email;

    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$",
            message = "올바른 전화번호 형식을 지켜주세요 ex) 010-1234-5678")
    private String phone; // Refactor: 정규식과 Validation 메시지 클래스로 따로 뺄 것

    @NotEmpty(message = "회원 주소는 공백이 불가합니다.")
    private String address;

    private String account;
}
