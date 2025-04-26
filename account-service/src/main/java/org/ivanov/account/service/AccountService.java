package org.ivanov.account.service;


import org.ivanov.accountdto.account.CreateAccountDto;
import org.ivanov.accountdto.account.ResponseAccountDto;

public interface AccountService {
    ResponseAccountDto createAccount(CreateAccountDto dto);
}
