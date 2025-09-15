package com.example.main.service;

import com.example.main.dto.AccountDTO;
import com.example.main.dto.ResponseDTO;
import com.example.main.exception.CustomException;
import com.example.main.model.Account;
import com.example.main.repository.AccountRepository;
import com.example.main.util.APICode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("accountService")
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public ResponseDTO addAccount(AccountDTO accountDTO) {
        try {
            log.info("Create Account request for client:{}", accountDTO.getClientId());
            Account account = new Account();
            account.setIban(accountDTO.getIban());
            account.setBicSwift(accountDTO.getBicSwift());
            account.setClientId(accountDTO.getClientId());
            accountRepository.save(account);
            log.info("Account Created Successfully for client:{}", accountDTO.getClientId());
            return new ResponseDTO(APICode.SUCCESS, "Account created successfully");
        } catch (DataIntegrityViolationException e) {
            throw new CustomException(HttpStatus.CONFLICT, "Duplicate Account creation request");
        }
    }

    public ResponseDTO updateAccount(Long id, AccountDTO accountDTO) {
        log.info("Update Account request for client:{}", accountDTO.getClientId());

        Account updatedAccount = accountRepository.findById(id)
                .map(account -> {
                    account.setIban(accountDTO.getIban());
                    account.setBicSwift(accountDTO.getBicSwift());
                    return accountRepository.save(account);
                })
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Account with id "+id+" not found"));
        log.info("Account Updated Successfully for client:{}", accountDTO.getClientId());
        return new ResponseDTO(APICode.SUCCESS, "Account updated successfully");
    }

    public ResponseDTO deleteAccount(Long id) {
        log.info("Delete Account request for account:{}", id);

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Account with id "+id+" not found"));
        accountRepository.delete(account);
        log.info("Account Deleted Successfully");
        return new ResponseDTO(APICode.SUCCESS, "Account deleted successfully");
    }

    public ResponseDTO fetchAccounts(Long id, String clientId, String iban, String bicSwift) {
        List<Account> accountList = accountRepository.findAccounts(id, clientId, iban, bicSwift);
        return new ResponseDTO(APICode.SUCCESS, accountList.isEmpty()?"No accounts found":"List of accounts found", accountList);
    }

}
