package com.pentalog;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import com.pentalog.model.User;
import com.pentalog.repository.UserRepository;
import com.pentalog.service.UserService;

@RunWith(SpringRunner.class)
public class UserServiceTest {

	@Mock
	UserRepository userRepository;

	@InjectMocks
	UserService userService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetUsernameAndPasswordWhenListIsEmpty() {
		List<User> users = new ArrayList<>();
		Mockito.when(userRepository.findByUsernameAndPassword(Mockito.any(), Mockito.any())).thenReturn(users);
		assertEquals("Emtpy list should return Optional.empty()", userService.getByUsernameAndPassword("", ""),
				Optional.empty());
	}

	@Test
	public void testGetUsernameAndPasswordWhenListContainsTwoIdenticElementsShouldReturnNull() {
		List<User> users = new ArrayList<>();
		users.add(new User("nume", "parola"));
		users.add(new User("nume", "parola"));
		Mockito.when(userRepository.findByUsernameAndPassword(Mockito.any(), Mockito.any())).thenReturn(users);
		assertEquals("Emtpy list should return Optional.empty()",
				userService.getByUsernameAndPassword("nume", "parola"), Optional.empty());
	}

	@Test
	public void testGetUsernameAndPasswordWhenListContainsOneUser() {
		List<User> users = new ArrayList<>();
		User user = new User("nume", "parola");
		users.add(user);
		Mockito.when(userRepository.findByUsernameAndPassword(Mockito.any(), Mockito.any())).thenReturn(users);
		assertEquals("Emtpy list should return Optional.empty()",
				userService.getByUsernameAndPassword("nume", "parola"), Optional.of(user));
	}
}
