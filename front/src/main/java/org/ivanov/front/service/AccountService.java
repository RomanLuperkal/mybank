package org.ivanov.front.service;

import org.ivanov.accountdto.account.CreateAccountDto;
import org.ivanov.accountdto.account.ResponseAccountDto;
import org.ivanov.accountdto.account.UpdatePasswordDto;
import org.ivanov.accountdto.wallet.ResponseWalletDto;

import java.util.Set;

public interface AccountService {
    ResponseAccountDto registration(CreateAccountDto dto);

    void deleteAccount(Long accountId, Set<ResponseWalletDto> wallets);

    String updatePassword(Long accountId, UpdatePasswordDto newPassword);
}
