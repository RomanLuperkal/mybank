package org.ivanov.account.service;

import org.blog.cashdto.transaction.ApprovedTransactionDto;
import org.ivanov.accountdto.wallet.CreateWalletDto;
import org.ivanov.accountdto.wallet.ReqWalletInfoDto;
import org.ivanov.accountdto.wallet.ResponseExchangeWalletsDto;
import org.ivanov.accountdto.wallet.ResponseWalletDto;
import org.ivanov.transferdto.ReqExchangeWalletsDto;
import org.ivanov.transferdto.ReqTransferMoneyDto;

import java.util.Set;

public interface WalletService {
    ResponseWalletDto createWallet(Long accountId, CreateWalletDto createWalletDto);

    ResponseWalletDto deleteWallet(Long accountId, Long walletId);

    void updateWallet(Long accountId,Long walletId, ApprovedTransactionDto dto);

    ResponseExchangeWalletsDto getWallet(ReqExchangeWalletsDto dto);

    void processTransferWallet(ReqTransferMoneyDto dto);

    Set<ResponseWalletDto> getWalletInfo(ReqWalletInfoDto dto);
}
