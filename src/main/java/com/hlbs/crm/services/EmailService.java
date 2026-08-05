package com.hlbs.crm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailService {
	@Autowired
	private JavaMailSender javaMailSender;

	@Async
	public void sendReminder(final Task task) throws MailException {
		final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

		simpleMailMessage.setTo("");
		simpleMailMessage.setSubject("CRM - Action required.");
		simpleMailMessage.setText("");

		javaMailSender.send(simpleMailMessage);
	}
}
