package com.pentalog;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.pentalog.model.Authentification;
import com.pentalog.model.User;
import com.pentalog.repository.AuthentificationRepository;
import com.pentalog.service.AuthentificationService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class AuthentficationServiceTest {

	@Mock
	AuthentificationRepository authentificationRepository;

	@InjectMocks
	AuthentificationService authentificationService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetUserByTokenShouldReturnUser() {
		List<Authentification> test = new ArrayList<>();
		Authentification authentificaton = new Authentification();
		User user = new User();
		authentificaton.setToken("abc");
		authentificaton.setUser(user);
		test.add(authentificaton);
		Mockito.when(authentificationRepository.findByToken(Mockito.any())).thenReturn(test);
		assertEquals("token in list returns Optional<User> ", authentificationService.getUserByToken("abc"),
				Optional.ofNullable(user));
	}

	@Test
	public void testGetUserByTokenShouldReturnEmptyOptional() {
		List<Authentification> test = new ArrayList<>();

		Mockito.when(authentificationRepository.findByToken(Mockito.any())).thenReturn(test);
		assertEquals("empty list returns null", authentificationService.getUserByToken("abc"), Optional.empty());
	}

	@Test
	public void testSave() {
		List<Authentification> test = new ArrayList<>();
		Authentification authentificaton = new Authentification();
		authentificaton.setToken("abc");
		User user = new User();
		authentificaton.setUser(user);
		Mockito.when(authentificationRepository.findByToken(Mockito.any())).thenReturn(test);
		Mockito.when(authentificationRepository.save(Mockito.any())).thenReturn(authentificaton);

		ArgumentCaptor<Authentification> valueCapture = ArgumentCaptor.forClass(Authentification.class);
		doNothing().when(authentificationRepository).delete(valueCapture.capture());
		ReflectionTestUtils.setField(authentificationService, "time", 20);

		authentificationService.save(authentificaton);

		verify(authentificationRepository).save(authentificaton);
		verify(authentificationRepository).findByToken(Mockito.any());
	}

	@Test
	public void testDelete() {
		List<Authentification> test = new ArrayList<>();
		Authentification authentificaton = new Authentification();
		authentificaton.setToken("abc");
		User user = new User();
		authentificaton.setUser(user);
		test.add(authentificaton);
		Mockito.when(authentificationRepository.findByToken(Mockito.any())).thenReturn(test);
		authentificationService.delete("abc");
		verify(authentificationRepository).delete(Mockito.any());
	}

}
