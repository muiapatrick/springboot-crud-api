package com.example.main.controller;

import com.example.main.dto.AccountDTO;
import com.example.main.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    //create account
    @PostMapping
    public ResponseEntity<?> createAccount(@Valid @RequestBody AccountDTO request) {
        return ResponseEntity.ok(accountService.addAccount(request));
    }

    //update account
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable Long id, @Valid @RequestBody AccountDTO request) {
        return ResponseEntity.ok(accountService.updateAccount(id, request));
    }

    //Get account/accounts
    @GetMapping()
    public ResponseEntity<?> fetchAccounts(@RequestParam(required = false) Long id,
                                           @RequestParam(required = false) String clientId,
                                           @RequestParam(required = false) String iban,
                                           @RequestParam(required = false) String bicSwift) {
        return ResponseEntity.ok(accountService.fetchAccounts(id, clientId, iban, bicSwift));
    }

    //delete account
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.deleteAccount(id));
    }
}
