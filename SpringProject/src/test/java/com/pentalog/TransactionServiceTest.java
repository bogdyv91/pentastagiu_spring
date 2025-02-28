package com.pentalog;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import com.pentalog.converter.TransactionConverter;
import com.pentalog.dto.TransactionDTO;
import com.pentalog.exceptions.ImpossibleToTransferException;
import com.pentalog.model.Account;
import com.pentalog.model.User;
import com.pentalog.repository.AccountRepository;
import com.pentalog.repository.NotificationRepository;
import com.pentalog.repository.TransactionRepository;
import com.pentalog.service.AccountService;
import com.pentalog.service.TransactionService;
import com.pentalog.utilities.enums.Currency;

@RunWith(SpringRunner.class)
public class TransactionServiceTest {

	@Mock
	AccountRepository accountRepository;

	@Mock
	AccountService accountService;

	@Mock
	TransactionRepository transactionRepository;

	@Mock
	TransactionConverter transactionConverter;

	@Mock
	NotificationRepository notificationRepository;

	@InjectMocks
	TransactionService transactionService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testMakeNewTransactionsWhenTransferIsPossible() {
		Account accountFrom = new Account("RO9789789787987987989781", null, new BigDecimal(1000), Currency.RON);
		Account accountTo = new Account("RO9789789787987987989782", null, new BigDecimal(1000), Currency.RON);
		TransactionDTO transfer = new TransactionDTO("RO9789789787987987989781", new BigDecimal(2), "detalii",
				"RO9789789787987987989782");
		Mockito.when(accountService.getAccountByAccountNumber("RO9789789787987987989781"))
				.thenReturn(Optional.of(accountFrom));
		Mockito.when(accountService.getAccountByAccountNumber("RO9789789787987987989782"))
				.thenReturn(Optional.of(accountTo));

		doNothing().when(accountRepository).save(Mockito.any());
		doNothing().when(transactionRepository).save(Mockito.any());
		doNothing().when(notificationRepository).save(Mockito.any());

		transactionService.makeNewTransaction(transfer);

		verify(accountRepository, times(2)).save(Mockito.any());
		verify(transactionRepository, times(2)).save(Mockito.any());
		verify(notificationRepository, times(2)).save(Mockito.any());
	}

	@Test(expected = ImpossibleToTransferException.class)
	public void testMakeNewTransactionsWhenTransferIsImpossibleShouldThrowException()
			throws ImpossibleToTransferException {
		Account accountFrom = new Account("RO9789789787987987989781", null, new BigDecimal(1000), Currency.RON);
		Account accountTo = new Account("RO9789789787987987989782", null, new BigDecimal(1000), Currency.RON);
		TransactionDTO transfer = new TransactionDTO("RO9789789787987987989781", new BigDecimal(20000), "detalii",
				"RO9789789787987987989782");
		Mockito.when(accountService.getAccountByAccountNumber("RO9789789787987987989781"))
				.thenReturn(Optional.of(accountFrom));
		Mockito.when(accountService.getAccountByAccountNumber("RO9789789787987987989782"))
				.thenReturn(Optional.of(accountTo));

		doNothing().when(accountRepository).save(Mockito.any());
		doNothing().when(transactionRepository).save(Mockito.any());
		doNothing().when(notificationRepository).save(Mockito.any());

		transactionService.makeNewTransaction(transfer);

		verify(accountRepository, times(0)).save(Mockito.any());
		verify(transactionRepository, times(0)).save(Mockito.any());
		verify(notificationRepository, times(0)).save(Mockito.any());
	}

	@Test
	public void testGetTransactionsByUserWhenUserIsNullShouldReturnEmptyList() {
		assertEquals(transactionService.getTransactionsByUser(null).size(), 0);
	}

	@Test
	public void testGetTransactionsByUserWhenUserHasNoTransactionShouldReturnEmptyList() {
		User user = new User();
		Mockito.when(accountRepository.findByUser(Mockito.any())).thenReturn(new ArrayList<Account>());
		assertEquals(transactionService.getTransactionsByUser(user).size(), 0);

	}

}
