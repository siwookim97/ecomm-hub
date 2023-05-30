package com.likelion.ecommhub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.likelion.ecommhub.domain.MailDto;
import com.likelion.ecommhub.service.MailService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MailController {
	private final MailService mailService;

	@GetMapping("/mail")
	public String showMailForm(Model model) {
		model.addAttribute("mailDto", new MailDto());
		return "mailForm";
	}

	@PostMapping("/mail")
	public String execMail(@ModelAttribute("mailDto") MailDto mailDto) {
		mailService.mailSend(mailDto);
		return "mailSent";
	}
}