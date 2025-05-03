package org.ivanov.account.service.impl;

import lombok.RequiredArgsConstructor;
import org.ivanov.account.handler.exception.AccountException;
import org.ivanov.account.mapper.AccountMapper;
import org.ivanov.account.model.Account;
import org.ivanov.account.repository.AccountRepository;
import org.ivanov.account.service.AccountService;
import org.ivanov.accountdto.account.CreateAccountDto;
import org.ivanov.accountdto.account.ResponseAccountDto;
import org.ivanov.accountdto.account.ResponseAccountInfoDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @PreAuthorize("hasAuthority('ACCOUNT_ROLE')")
    public ResponseAccountDto createAccount(CreateAccountDto dto) {
        if (accountRepository.existsAccountByUsername(dto.username())) {
            throw new AccountException(HttpStatus.CONFLICT, "Аккаунт с именем: " + dto.username() + " уже существует.");
        }

        Account newAccount = accountMapper.mapToAccount(dto);
        newAccount.setPassword(passwordEncoder.encode(dto.password()));
        Account createdAccount = accountRepository.save(newAccount);
        //TODO отправка уведомления в NOTIFICATION
        return accountMapper.mapToResponseAccount(createdAccount);
    }

    @Override
    @PreAuthorize("hasAuthority('account_role')")
    public ResponseAccountInfoDto getAccountInfo(String username) {
        if (!accountRepository.existsAccountByUsername(username)) {
            throw new AccountException(HttpStatus.CONFLICT, "Аккаунта с именем: " + username + " не существует.");
        }

        Account account = accountRepository.findAccountByUsername(username);
        return accountMapper.mapToResponseAccountInfoDto(account);
    }
}
