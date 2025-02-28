package com.pentalog.utilities.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.pentalog.model.Notification;

@Service
public class EmailServiceImpl {

	@Autowired
	public JavaMailSender emailSender;

	/**
	 * Sends the simple message using JavaMailSender object
	 * 
	 * @param to
	 * @param subject
	 * @param text
	 * @see JavaMailSender
	 */
	public void sendSimpleMessage(String to, String subject, String text) {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);

		emailSender.send(message);
	}

	/**
	 * Creates a message for the specified notification
	 * 
	 * @param notification
	 * @return
	 */
	public String createMessage(Notification notification) {
		String message = "You have a new " + notification.getTransaction().getType().toString().toLowerCase()
				+ " transaction on account " + notification.getTransaction().getAccount() + ", amount transferred is "
				+ notification.getTransaction().getAmount() + " and the details of the transaction are: "
				+ notification.getTransaction().getDetails();
		return message;
	}

}
