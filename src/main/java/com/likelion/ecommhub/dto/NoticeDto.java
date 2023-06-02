package com.likelion.ecommhub.dto;

import com.likelion.ecommhub.domain.NoticeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDto {

    @NotEmpty(message = "공지 타입을 선택해주세요.")
    private NoticeType noticeType;
    @NotEmpty(message = "제목은 공백이 불가합니다.")
    private String title;
    @NotEmpty(message = "내용은 공백이 불가합니다.")
    private String content;

}
