package com.pentalog;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
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

import com.pentalog.converter.AccountConverter;
import com.pentalog.dto.AccountDTO;
import com.pentalog.exceptions.ImpossibleToInsertAccountException;
import com.pentalog.model.Account;
import com.pentalog.model.User;
import com.pentalog.repository.AccountRepository;
import com.pentalog.service.AccountService;
import com.pentalog.utilities.enums.Currency;

@RunWith(SpringRunner.class)
public class AccountServiceTest {

	@Mock
	AccountRepository accountRepository;

	@Mock
	AccountConverter accountConverter;

	@InjectMocks
	AccountService accountService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAccountByAccountNumberWhenNoAccountWasFound() {
		Mockito.when(accountRepository.findByAccountNumber(Mockito.any())).thenReturn(new ArrayList<Account>());
		assertEquals("If no account with the specified number was found should return Optional.empty",
				accountService.getAccountByAccountNumber(""), Optional.empty());
	}

	@Test
	public void testGetAccountByAccountNumberWhenMoreThanOneAccountWithSameNumberFound() {
		Account account = new Account();
		account.setAccountNumber("RO123");
		List<Account> accounts = new ArrayList<>();
		accounts.add(account);
		accounts.add(account);
		Mockito.when(accountRepository.findByAccountNumber(Mockito.any())).thenReturn(accounts);
		assertEquals("If more than one account with the specified number was found should return Optional.empty",
				accountService.getAccountByAccountNumber("RO123"), Optional.empty());
	}

	@Test
	public void testGetAccountByAccountNumberWhenOneAccountFound() {
		Account account = new Account();
		account.setAccountNumber("RO123");
		List<Account> accounts = new ArrayList<>();
		accounts.add(account);
		Mockito.when(accountRepository.findByAccountNumber(Mockito.any())).thenReturn(accounts);
		assertEquals("If one account with the specified number was found should return Optional.empty",
				accountService.getAccountByAccountNumber("RO123"), Optional.of(account));
	}

	@Test
	public void testGetAccountsDtoForUserWhenUserIsNull() {
		Mockito.when(accountRepository.findByUser(Mockito.any())).thenReturn(new ArrayList<>());
		assertEquals("If user is null should return empty list", accountService.getAccountsDTOForUser(null).size(), 0);
	}

	@Test
	public void testGetAccountsDtoForUserWhenUserIsNotNullAndHasNoAccounts() {
		Mockito.when(accountRepository.findByUser(Mockito.any())).thenReturn(new ArrayList<>());
		assertEquals("If user is not null but has no accounts should return empty list",
				accountService.getAccountsDTOForUser(new User()).size(), 0);
	}

	@Test
	public void testGetAccountsDtoForUserWhenUserIsNotNullAndHasAccounts() {
		List<Account> accounts = new ArrayList<>();
		accounts.add(new Account("RO123", null, new BigDecimal(1), Currency.RON));
		Mockito.when(accountRepository.findByUser(Mockito.any())).thenReturn(accounts);
		assertEquals("If user is not null and has accounts should return a list of AccountDTO",
				accountService.getAccountsDTOForUser(new User()).size(), 1);
	}

	@Test(expected = ImpossibleToInsertAccountException.class)
	public void testSaveAccountByDTOAndUserShouldThrowExceptionWhenAccountAlreadyInDB() {
		List<Account> accounts = new ArrayList<>();
		accounts.add(new Account("RO123", null, new BigDecimal(1), Currency.RON));
		Mockito.when(accountRepository.findByUser(Mockito.any())).thenReturn(accounts);

		accountService.saveAccountByDTOAndUser(new AccountDTO("RO123", Currency.RON, new BigDecimal(1)), null);

		verify(accountRepository, times(0)).save(Mockito.any());
	}

	@Test(expected = ImpossibleToInsertAccountException.class)
	public void testSaveAccountByDTOAndUserShouldThrowExceptionWhenAccountNumberNotCorrect() {
		Mockito.when(accountRepository.findByUser(Mockito.any())).thenReturn(new ArrayList<>());

		accountService.saveAccountByDTOAndUser(new AccountDTO("RO123", Currency.RON, new BigDecimal(1)), null);

		verify(accountRepository, times(0)).save(Mockito.any());
	}

	@Test
	public void testSaveAccountByDTOAndUserShouldShouldAddInDB() {
		Mockito.when(accountRepository.findByUser(Mockito.any())).thenReturn(new ArrayList<>());

		accountService.saveAccountByDTOAndUser(
				new AccountDTO("RO0988900980980980989801", Currency.RON, new BigDecimal(1)), null);

		verify(accountRepository, times(1)).save(Mockito.any());
	}

}
