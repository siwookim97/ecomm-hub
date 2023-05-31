package com.likelion.ecommhub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.likelion.ecommhub.domain.MailDto;


@Service
@RequiredArgsConstructor
public class MailService {

	private final JavaMailSender javaMailSender;
	@Value("${spring.mail.username}")
	private String from;

	@Async("EmailAsync")
	public void mailSend(MailDto mailDto){
		SimpleMailMessage  message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(mailDto.getAddress());
		message.setSubject(mailDto.getTitle());
		message.setText(mailDto.getMessage());
		javaMailSender.send(message);
	}
}