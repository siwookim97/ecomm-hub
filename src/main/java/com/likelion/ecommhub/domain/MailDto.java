package com.likelion.ecommhub.domain;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Valid
public class MailDto {
	private String address;
	private String title;
	private String message;
	private MultipartFile file;
}