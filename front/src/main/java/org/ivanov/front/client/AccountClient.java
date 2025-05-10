package org.ivanov.front.client;

import org.ivanov.accountdto.account.CreateAccountDto;
import org.ivanov.accountdto.account.ResponseAccountDto;

public interface AccountClient {
    ResponseAccountDto registration(CreateAccountDto dto);

    ResponseAccountDto getAccount(String username);
}
