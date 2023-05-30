package com.likelion.ecommhub.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.likelion.ecommhub.domain.MailDto;
import com.likelion.ecommhub.service.MailService;

@ExtendWith(MockitoExtension.class)
public class MailServiceTest {

	@Mock
	private JavaMailSender javaMailSender;

	@InjectMocks
	private MailService mailService;

	@Test
	public void testMailSend() {
		// given
		MailDto mailDto = new MailDto();
		mailDto.setAddress("test@example.com");
		mailDto.setTitle("Test Title");
		mailDto.setMessage("Test Message");

		SimpleMailMessage expectedMessage = new SimpleMailMessage();
		expectedMessage.setTo(mailDto.getAddress());
		expectedMessage.setSubject(mailDto.getTitle());
		expectedMessage.setText(mailDto.getMessage());

		// when
		mailService.mailSend(mailDto);

		// then
		Mockito.verify(javaMailSender, Mockito.times(1)).send(expectedMessage);
	}
}
