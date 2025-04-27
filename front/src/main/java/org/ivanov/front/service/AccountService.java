package org.ivanov.front.service;

import org.ivanov.accountdto.account.CreateAccountDto;
import org.ivanov.accountdto.account.ResponseAccountDto;

public interface AccountService {
    ResponseAccountDto registration(CreateAccountDto dto);
}
