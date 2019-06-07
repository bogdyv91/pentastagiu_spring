package com.pentalog.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.pentalog.model.Notification;
import com.pentalog.model.Person;
import com.pentalog.repository.NotificationRepository;
import com.pentalog.repository.PersonRepository;
import com.pentalog.utilities.email.EmailServiceImpl;
import com.pentalog.utilities.enums.Status;

/**
 * Service class for operations with Notification objects
 * 
 * @author Vacariuc Bogdan
 *
 */
@Service
public class NotificationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);

	@Autowired
	NotificationRepository notificationRepsitory;

	@Autowired
	EmailServiceImpl emailServiceImpl;

	@Autowired
	PersonRepository personRepository;

	/**
	 * Schedueled at 15 seconds to search into notification table for any not sent
	 * notifications and send emails for them
	 */
	@Scheduled(fixedDelay = 15000)
	public void sendEmails() {
		List<Notification> notifications = notificationRepsitory.findBy();
		for (Notification notification : notifications) {
			if (notification.getStatus() == Status.NOT_SENT) {
				Optional<Person> person = personRepository.findPersonByUser(notification.getUser());
				if (person.isPresent()) {
					String message = emailServiceImpl.createMessage(notification);
					try {
						emailServiceImpl.sendSimpleMessage(person.get().getEmail(), "New transaction", message);
						notification.setSentTime(LocalDateTime.now());
						notification.setStatus(Status.SENT);

					} catch (MailException e) {
						LOGGER.error("Mail could not be sent");
					}
					notificationRepsitory.save(notification);
				}
			}
		}
	}

}
