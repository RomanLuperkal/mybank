package org.ivanov.account.service;

import org.blog.cashdto.transaction.ApprovedTransactionDto;
import org.ivanov.accountdto.wallet.CreateWalletDto;
import org.ivanov.accountdto.wallet.ResponseWalletDto;

public interface WalletService {
    ResponseWalletDto createWallet(Long accountId, CreateWalletDto createWalletDto);

    ResponseWalletDto deleteWallet(Long accountId, Long walletId);

    void updateWallet(Long accountId,Long walletId, ApprovedTransactionDto dto);

    ResponseWalletDto getWallet(Long walletId);
}
