package org.ivanov.account.service;

import org.ivanov.accountdto.wallet.CreateWalletDto;
import org.ivanov.accountdto.wallet.ResponseWalletDto;

public interface WalletService {
    ResponseWalletDto createWallet(Long accountId, CreateWalletDto createWalletDto);

    ResponseWalletDto deleteWallet(Long accountId, Long walletId);
}
