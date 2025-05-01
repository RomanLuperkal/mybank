package org.ivanov.account.service;


import org.ivanov.accountdto.account.CreateAccountDto;
import org.ivanov.accountdto.account.ResponseAccountDto;
import org.ivanov.accountdto.account.ResponseAccountInfoDto;

public interface AccountService {
    ResponseAccountDto createAccount(CreateAccountDto dto);

    ResponseAccountInfoDto getAccountInfo(String username);

}
