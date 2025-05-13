package org.ivanov.account.service.impl;

import lombok.RequiredArgsConstructor;
import org.ivanov.account.handler.exception.AccountException;
import org.ivanov.account.mapper.AccountMapper;
import org.ivanov.account.model.Account;
import org.ivanov.account.repository.AccountRepository;
import org.ivanov.account.service.AccountService;
import org.ivanov.accountdto.account.CreateAccountDto;
import org.ivanov.accountdto.account.ResponseAccountDto;
import org.ivanov.accountdto.account.UpdatePasswordDto;
import org.ivanov.accountdto.account.UpdateProfileDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ACCOUNT_ROLE')")
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
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
    public ResponseAccountDto getAccountInfo(String username) {
        if (!accountRepository.existsAccountByUsername(username)) {
            throw new AccountException(HttpStatus.CONFLICT, "Аккаунта с именем: " + username + " не существует.");
        }

        Account account = accountRepository.findAccountByUsername(username);
        return accountMapper.mapToResponseAccount(account);
    }

    @Override
    public void deleteAccount(long accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new AccountException(HttpStatus.CONFLICT, "Аккаунта с id= " + accountId + " не существует.");
        }
        accountRepository.deleteById(accountId);
    }

    @Override
    public void updatePassword(Long accountId, UpdatePasswordDto newPassword) {
       Account account = findAccountById(accountId);

       account.setPassword(newPassword.password());
       accountRepository.save(account);
    }

    @Override
    public void updateProfile(Long accountId, UpdateProfileDto newProfile) {
        Account account = findAccountById(accountId);
        Account updatedAccount = accountMapper.mapToProduct(account, newProfile);
        accountRepository.save(updatedAccount);
    }

    private Account findAccountById(long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountException(HttpStatus.CONFLICT, "Аккаунта с id= " + accountId + " не существует."));
    }
}
