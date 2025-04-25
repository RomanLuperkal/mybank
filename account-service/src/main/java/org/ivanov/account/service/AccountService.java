package org.ivanov.account.service;

import org.ivanov.account.dto.account.ResponseAccountDto;
import org.ivanov.account.dto.account.CreateAccountDto;

public interface AccountService {
    ResponseAccountDto createAccount(CreateAccountDto dto);
}
