package org.ivanov.front.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ivanov.accountdto.account.CreateAccountDto;
import org.ivanov.accountdto.account.ResponseAccountDto;
import org.ivanov.accountdto.account.UpdatePasswordDto;
import org.ivanov.accountdto.account.UpdateProfileDto;
import org.ivanov.accountdto.wallet.ResponseWalletDto;
import org.ivanov.front.client.AccountClient;
import org.ivanov.front.handler.exception.AccountException;
import org.ivanov.front.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountClient accountClient;
    private final PasswordEncoder passwordEncoder;
    private final AccountClient client;

    @Override
    public ResponseAccountDto registration(CreateAccountDto dto) {
        return accountClient.registration(dto);
    }

    @Override
    public void deleteAccount(Long accountId, Set<ResponseWalletDto> wallets) {
        boolean isZero = wallets.stream().map(w -> w.balance().compareTo(BigDecimal.ZERO)).anyMatch(res -> res > 0);
        if (isZero) {
            throw new AccountException("Нельзя удалить аккаунт с ненулевыми счетами", HttpStatus.CONFLICT.toString());
        }
        accountClient.deleteAccount(accountId);
    }

    @Override
    public String updatePassword(Long accountId, UpdatePasswordDto newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword.password());
        accountClient.updatePassword(accountId, new UpdatePasswordDto(encodedPassword));
        return encodedPassword;
    }

    @Override
    public void updateProfile(Long accountId, UpdateProfileDto profile) {
        accountClient.updateProfile(accountId, profile);
    }

    @Override
    public ResponseAccountDto getAccountInfo(String username) {
        ResponseAccountDto account = client.getAccount(username);
        log.debug("accountInfo: {}", account);
        return account;
    }
}
