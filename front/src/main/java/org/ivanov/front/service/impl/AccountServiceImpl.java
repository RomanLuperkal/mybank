package org.ivanov.front.service.impl;

import lombok.RequiredArgsConstructor;
import org.ivanov.accountdto.account.CreateAccountDto;
import org.ivanov.accountdto.account.ResponseAccountDto;
import org.ivanov.front.client.AccountClient;
import org.ivanov.front.service.AccountService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountClient accountClient;

    @Override
    public ResponseAccountDto registration(CreateAccountDto dto) {
        return accountClient.registration(dto);
    }
}
