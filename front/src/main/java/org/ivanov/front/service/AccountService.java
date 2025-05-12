package org.ivanov.front.service;

import org.ivanov.accountdto.account.CreateAccountDto;
import org.ivanov.accountdto.account.ResponseAccountDto;
import org.ivanov.accountdto.wallet.ResponseWalletDto;
import org.ivanov.front.configuration.security.AccountUserDetails;

import java.util.Set;

public interface AccountService {
    ResponseAccountDto registration(CreateAccountDto dto);

    void deleteAccount(Long accountId, Set<ResponseWalletDto> wallets);
}
