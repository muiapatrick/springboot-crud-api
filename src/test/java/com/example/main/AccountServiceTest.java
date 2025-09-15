package com.example.main;

import com.example.main.dto.AccountDTO;
import com.example.main.dto.ResponseDTO;
import com.example.main.exception.CustomException;
import com.example.main.model.Account;
import com.example.main.repository.AccountRepository;
import com.example.main.service.AccountService;
import com.example.main.util.APICode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private AccountDTO accountDTO;
    private Account account;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        accountDTO = new AccountDTO();
        accountDTO.setClientId("C123");
        accountDTO.setIban("KE123");
        accountDTO.setBicSwift("BIC123");

        account = new Account();
        account.setId(1L);
        account.setClientId("C123");
        account.setIban("KE123");
        account.setBicSwift("BIC123");
    }

    @Test
    void addAccount_success() {
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        ResponseDTO response = accountService.addAccount(accountDTO);

        assertEquals(APICode.SUCCESS.getCode(), response.getStatusCode());
        assertEquals("Account created successfully", response.getMessage());
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void addAccount_duplicate_throwsCustomException() {
        when(accountRepository.save(any(Account.class)))
                .thenThrow(new DataIntegrityViolationException("duplicate"));

        CustomException ex = assertThrows(CustomException.class,
                () -> accountService.addAccount(accountDTO));

        assertEquals(HttpStatus.CONFLICT, ex.getHttpStatus());
        assertEquals("Duplicate Account creation request", ex.getMessage());
    }

    @Test
    void updateAccount_success() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        ResponseDTO response = accountService.updateAccount(1L, accountDTO);

        assertEquals(APICode.SUCCESS.getCode(), response.getStatusCode());
        assertEquals("Account updated successfully", response.getMessage());
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void updateAccount_notFound_throwsCustomException() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        CustomException ex = assertThrows(CustomException.class,
                () -> accountService.updateAccount(1L, accountDTO));

        assertEquals(HttpStatus.NOT_FOUND, ex.getHttpStatus());
        assertTrue(ex.getMessage().contains("Account with id 1 not found"));
    }

    @Test
    void deleteAccount_success() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        ResponseDTO response = accountService.deleteAccount(1L);

        assertEquals(APICode.SUCCESS.getCode(), response.getStatusCode());
        assertEquals("Account deleted successfully", response.getMessage());
        verify(accountRepository, times(1)).delete(account);
    }

    @Test
    void deleteAccount_notFound_throwsCustomException() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        CustomException ex = assertThrows(CustomException.class,
                () -> accountService.deleteAccount(1L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getHttpStatus());
        assertTrue(ex.getMessage().contains("Account with id 1 not found"));
    }

    @Test
    void fetchAccounts_withResults() {
        when(accountRepository.findAccounts(null, "C123", null, null))
                .thenReturn(List.of(account));

        ResponseDTO response = accountService.fetchAccounts(null, "C123", null, null);

        assertEquals(APICode.SUCCESS.getCode(), response.getStatusCode());
        assertEquals("List of accounts found", response.getMessage());
        assertFalse(((List<?>) response.getData()).isEmpty());
    }

    @Test
    void fetchAccounts_noResults() {
        when(accountRepository.findAccounts(null, "C123", null, null))
                .thenReturn(Collections.emptyList());

        ResponseDTO response = accountService.fetchAccounts(null, "C123", null, null);

        assertEquals(APICode.SUCCESS.getCode(), response.getStatusCode());
        assertEquals("No accounts found", response.getMessage());
        assertTrue(((List<?>) response.getData()).isEmpty());
    }
}