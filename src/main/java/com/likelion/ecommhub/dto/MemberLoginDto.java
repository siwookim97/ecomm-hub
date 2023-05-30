package com.likelion.ecommhub.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberLoginDto {

    @NotEmpty(message = "회원 아이디는 공백이 불가합니다.")
    private String loginId;

    @NotEmpty(message = "회원 비밀번호는 공백이 불가합니다.")
    private String password;
}
