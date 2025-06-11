package org.ivanov.account.service.impl;

import lombok.RequiredArgsConstructor;
import org.ivanov.account.handler.exception.AccountException;
import org.ivanov.account.mapper.AccountMapper;
import org.ivanov.account.model.Account;
import org.ivanov.account.model.NotificationOutBox;
import org.ivanov.account.repository.AccountRepository;
import org.ivanov.account.service.AccountService;
import org.ivanov.account.service.NotificationOutBoxService;
import org.ivanov.accountdto.account.CreateAccountDto;
import org.ivanov.accountdto.account.ResponseAccountDto;
import org.ivanov.accountdto.account.UpdatePasswordDto;
import org.ivanov.accountdto.account.UpdateProfileDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ACCOUNT_ROLE')")
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;
    private final NotificationOutBoxService notificationOutBoxService;

    @Override
    @Transactional
    public ResponseAccountDto createAccount(CreateAccountDto dto) {
        if (accountRepository.existsAccountByUsername(dto.username())) {
            throw new AccountException(HttpStatus.CONFLICT, "Аккаунт с именем: " + dto.username() + " уже существует.");
        }

        Account newAccount = accountMapper.mapToAccount(dto);
        newAccount.setPassword(passwordEncoder.encode(dto.password()));
        Account createdAccount = accountRepository.save(newAccount);
        NotificationOutBox prepareMessage = notificationOutBoxService
                .createNotificationOutBoxMessage("Действия над учетной записью пользователя",
                        "Аккаунт успешно создан", createdAccount.getEmail());
        notificationOutBoxService.createNotificationOutBoxMessage(prepareMessage);

        return accountMapper.mapToResponseAccount(createdAccount);
    }

    @Override
    public ResponseAccountDto getAccountInfo(String username) {
        Account account = accountRepository.findAccountByUsername(username)
                .orElseThrow(() -> new AccountException(HttpStatus.CONFLICT, "Аккаунта с именем: " + username + " не существует."));
        return accountMapper.mapToResponseAccount(account);
    }

    @Override
    @Transactional
    public void deleteAccount(long accountId) {
        Account account = findAccountById(accountId);

        accountRepository.delete(account);
        NotificationOutBox prepareMessage = notificationOutBoxService
                .createNotificationOutBoxMessage("Действия над учетной записью пользователя",
                        "Аккаунт успешно удален", account.getEmail());
        notificationOutBoxService.createNotificationOutBoxMessage(prepareMessage);
    }

    @Override
    public void updatePassword(Long accountId, UpdatePasswordDto newPassword) {
       Account account = findAccountById(accountId);

       account.setPassword(newPassword.password());
       accountRepository.save(account);
        NotificationOutBox prepareMessage = notificationOutBoxService
                .createNotificationOutBoxMessage("Действия над учетной записью пользователя",
                        "Ваш пароль от аккаунта успешно обновлен", account.getEmail());
        notificationOutBoxService.createNotificationOutBoxMessage(prepareMessage);
    }

    @Override
    public void updateProfile(Long accountId, UpdateProfileDto newProfile) {
        Account account = findAccountById(accountId);
        Account updatedAccount = accountMapper.mapToProduct(account, newProfile);
        accountRepository.save(updatedAccount);

        NotificationOutBox prepareMessage = notificationOutBoxService
                .createNotificationOutBoxMessage("Действия над учетной записью пользователя",
                        "Ваш профиль аккаунта успешно обновлен", account.getEmail());
        notificationOutBoxService.createNotificationOutBoxMessage(prepareMessage);
    }


    private Account findAccountById(long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountException(HttpStatus.CONFLICT, "Аккаунта с id= " + accountId + " не существует."));
    }
}
