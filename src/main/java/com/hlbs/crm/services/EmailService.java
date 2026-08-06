package com.hlbs.crm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

	@Value("${spring.mail.username}")
	private String senderEmail;

	@Async
	public void sendReminder(final String recipientEmail, final String emailSubject, final String emailBody) throws MailException {
		final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

		simpleMailMessage.setFrom(senderEmail);
		simpleMailMessage.setTo(recipientEmail);
		simpleMailMessage.setSubject(emailSubject);
		simpleMailMessage.setText(emailBody);

		javaMailSender.send(simpleMailMessage);
	}
}
