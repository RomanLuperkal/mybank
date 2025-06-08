package org.ivanov.transfer.client;


import org.ivanov.accountdto.wallet.ResponseWalletDto;

public interface AccountClient {
    //void processTransaction(Long accountId, Long walletId, ApprovedTransactionDto dto);

    ResponseWalletDto getWallet(Long walletId);
}
