package com.likelion.ecommhub.dto;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MemberLoginDto {

    private String username;

    private String password;
}
