package com.likelion.ecommhub.domain;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MailDto {
	private String address;
	private String title;
	private String message;
	private MultipartFile file;
}