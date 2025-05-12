package org.ivanov.account.service;


import org.ivanov.accountdto.account.CreateAccountDto;
import org.ivanov.accountdto.account.ResponseAccountDto;
import org.ivanov.accountdto.account.UpdatePasswordDto;

public interface AccountService {
    ResponseAccountDto createAccount(CreateAccountDto dto);

    ResponseAccountDto getAccountInfo(String username);

    void deleteAccount(long accountId);

    void updatePassword(Long accountId, UpdatePasswordDto newPassword);

}
