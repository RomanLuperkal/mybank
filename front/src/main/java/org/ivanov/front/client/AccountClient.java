package org.ivanov.front.client;

import org.ivanov.accountdto.account.CreateAccountDto;
import org.ivanov.accountdto.account.ResponseAccountDto;
import org.ivanov.accountdto.account.UpdatePasswordDto;

public interface AccountClient {
    ResponseAccountDto registration(CreateAccountDto dto);

    ResponseAccountDto getAccount(String username);

    void deleteAccount(Long accountId);

    void changePassword(Long accountId, UpdatePasswordDto password);
}
