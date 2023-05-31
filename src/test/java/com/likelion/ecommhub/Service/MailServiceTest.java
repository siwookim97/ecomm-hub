package com.likelion.ecommhub.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.likelion.ecommhub.domain.MailDto;
import com.likelion.ecommhub.service.MailService;
import org.springframework.test.util.ReflectionTestUtils;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MailServiceTest {

	@Mock
	private JavaMailSender javaMailSender;

	@InjectMocks
	private MailService mailService;

	@Captor
	private ArgumentCaptor<SimpleMailMessage> mailMessageCaptor;

	@Test
	public void testMailSend() {
		// Given
		String testFrom = "test@example.com";
		ReflectionTestUtils.setField(mailService, "from", testFrom);
		String[] testAddress = {"test1@example.com"};
		String testTitle = "Test title";
		String testMessage = "Test message";

		MailDto testMailDto = new MailDto();
		testMailDto.setAddress(testAddress);
		testMailDto.setTitle(testTitle);
		testMailDto.setMessage(testMessage);

		// When
		mailService.mailSend(testMailDto);

		// Then
		Mockito.verify(javaMailSender, Mockito.times(1)).send(mailMessageCaptor.capture());
		SimpleMailMessage sentMessage = mailMessageCaptor.getValue();
		assertEquals(testFrom, sentMessage.getFrom());
		assertEquals(testAddress, sentMessage.getTo());
		assertEquals(testTitle, sentMessage.getSubject());
		assertEquals(testMessage, sentMessage.getText());
	}
}
