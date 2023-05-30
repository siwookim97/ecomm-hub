package com.likelion.ecommhub.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.likelion.ecommhub.domain.MailDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MailService {

	private JavaMailSender javaMailSender;
	private static final String FROM_ADDRESS = "hjkim2661@gmail.com";

	public void mailSend(MailDto mailDto){
		SimpleMailMessage  message = new SimpleMailMessage();
		message.setTo(mailDto.getAddress());
		message.setSubject(mailDto.getTitle());
		message.setText(mailDto.getMessage());
		javaMailSender.send(message);
	}
}