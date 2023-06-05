package com.likelion.ecommhub.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InquiryDto {

    @NotEmpty(message = "문의 제목은 공백이 불가합니다.")
    private String title;

    @NotEmpty(message = "문의 내용은 공백이 불가합니다.")
    private String content;
}
