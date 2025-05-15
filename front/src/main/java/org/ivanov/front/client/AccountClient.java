package org.ivanov.front.client;

import org.ivanov.accountdto.account.CreateAccountDto;
import org.ivanov.accountdto.account.ResponseAccountDto;
import org.ivanov.accountdto.account.UpdatePasswordDto;
import org.ivanov.accountdto.account.UpdateProfileDto;
import org.ivanov.accountdto.wallet.CreateWalletDto;
import org.ivanov.accountdto.wallet.ResponseWalletDto;

public interface AccountClient {
    ResponseAccountDto registration(CreateAccountDto dto);

    ResponseAccountDto getAccount(String username);

    void deleteAccount(Long accountId);

    void updatePassword(Long accountId, UpdatePasswordDto password);

    void updateProfile(Long accountId, UpdateProfileDto profile);

    ResponseWalletDto createWallet(Long accountId, CreateWalletDto createWalletDto);

    void deleteWallet(Long accountId, Long walletId);
}
