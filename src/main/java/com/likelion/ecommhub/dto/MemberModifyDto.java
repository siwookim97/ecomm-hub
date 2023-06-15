package com.likelion.ecommhub.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberModifyDto {

    private String nickname;
    private String email;
    private String phone;
    private String address;
    private String account;
}
