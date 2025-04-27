package org.ivanov.account.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ivanov.account.service.AccountService;


import org.ivanov.accountdto.account.CreateAccountDto;
import org.ivanov.accountdto.account.ResponseAccountDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/registration")
    public ResponseEntity<ResponseAccountDto> registry(@Valid @RequestBody CreateAccountDto createAccountDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(createAccountDto));
    }
}
