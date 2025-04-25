package org.ivanov.account.service.impl;

import lombok.RequiredArgsConstructor;
import org.ivanov.account.dto.account.CreateAccountDto;
import org.ivanov.account.dto.account.ResponseAccountDto;
import org.ivanov.account.handler.exception.AccountException;
import org.ivanov.account.mapper.AccountMapper;
import org.ivanov.account.model.Account;
import org.ivanov.account.repository.AccountRepository;
import org.ivanov.account.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public ResponseAccountDto createAccount(CreateAccountDto dto) {
        if (accountRepository.existsAccountByUsername(dto.getUsername())) {
            throw new AccountException(HttpStatus.CONFLICT, "Аккаунт с именем: " + dto.getUsername() + " уже существует.");
        }

        Account newAccount = accountMapper.mapToAccount(dto);
        Account createdAccount = accountRepository.save(newAccount);
        //TODO отправка уведомления в NOTIFICATION
        return accountMapper.mapToResponseAccount(createdAccount);
    }
}
