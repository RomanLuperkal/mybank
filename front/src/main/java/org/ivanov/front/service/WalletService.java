package org.ivanov.front.service;

import org.ivanov.accountdto.wallet.CreateWalletDto;
import org.ivanov.accountdto.wallet.ResponseWalletDto;

import java.util.Set;

public interface WalletService {
    ResponseWalletDto createWallet(Long accountId, CreateWalletDto createWalletDto, Set<ResponseWalletDto> wallets);

    void deleteWallet(Long accountId, Long walletId, Set<ResponseWalletDto> wallets);
}
