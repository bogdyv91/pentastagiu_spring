package com.pentalog.utilities.email;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class JavaMailSender {

	/**
	 * Configured the JavaMailSenderImpl object to send the mail
	 * 
	 * @return
	 */
	@Bean
	public JavaMailSenderImpl getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);

		mailSender.setUsername("webookiasi@gmail.com");
		mailSender.setPassword("parola123!");

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		// props.put("mail.debug", "true");

		return mailSender;
	}

	/**
	 * Sends the message
	 * 
	 * @param message
	 */
	public void send(SimpleMailMessage message) {
		this.getJavaMailSender().send(message);

	}
}
