package com.pentalog;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.pentalog.model.Notification;
import com.pentalog.model.Person;
import com.pentalog.repository.NotificationRepository;
import com.pentalog.repository.PersonRepository;
import com.pentalog.service.NotificationService;
import com.pentalog.utilities.email.EmailServiceImpl;
import com.pentalog.utilities.enums.Status;

public class NotificationServiceTest {

	@Mock
	NotificationRepository notificationRepsitory;
	
	@Mock
	EmailServiceImpl emailServiceImpl;
	
	@Mock
	PersonRepository personRepository;
	
	@InjectMocks
	NotificationService notificationService;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testSendEmailsWhenThereAreNoNotifications() {
		Mockito.when(notificationRepsitory.findBy()).thenReturn(new ArrayList<>());
		notificationService.sendEmails();
		verify(notificationRepsitory, times(0)).save(Mockito.any());
		
	}
	
	@Test
	public void testSendEmailsWhenAllNotificationsAreSent() {
		List<Notification> notifications=new ArrayList<>();
		Notification notification=new Notification();
		notification.setStatus(Status.SENT);
		notifications.add(notification);
		Mockito.when(notificationRepsitory.findBy()).thenReturn(notifications);
		notificationService.sendEmails();
		verify(notificationRepsitory, times(0)).save(Mockito.any());
		
	}
	
	@Test
	public void testSendEmailsWhenThereAreNotSentNotificationsButNoPerson() {
		List<Notification> notifications=new ArrayList<>();
		Notification notification=new Notification();
		notification.setStatus(Status.NOT_SENT);
		notifications.add(notification);
		Mockito.when(notificationRepsitory.findBy()).thenReturn(notifications);
		Mockito.when(personRepository.findPersonByUser(Mockito.any())).thenReturn(Optional.empty());
		notificationService.sendEmails();
		verify(notificationRepsitory, times(0)).save(Mockito.any());
		
	}
	
	@Test
	public void testSendEmailsWhenThereAreNotSentNotificationsAndThereIsPerson() {
		List<Notification> notifications=new ArrayList<>();
		Notification notification=new Notification();
		notification.setStatus(Status.NOT_SENT);
		notifications.add(notification);
		Mockito.when(notificationRepsitory.findBy()).thenReturn(notifications);
		Mockito.when(personRepository.findPersonByUser(Mockito.any())).thenReturn(Optional.of(new Person()));
		Mockito.when(emailServiceImpl.createMessage(Mockito.any())).thenReturn("");
		doNothing().when(emailServiceImpl).sendSimpleMessage(Mockito.any(), Mockito.any(), Mockito.any());
		notificationService.sendEmails();
		verify(notificationRepsitory, times(1)).save(Mockito.any());
		
	}
	
}
