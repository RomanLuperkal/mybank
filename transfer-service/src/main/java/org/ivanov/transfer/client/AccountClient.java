package org.ivanov.transfer.client;


import org.ivanov.accountdto.wallet.ResponseExchangeWalletsDto;
import org.ivanov.transferdto.ReqExchangeWalletsDto;
import org.ivanov.transferdto.ReqTransferMoneyDto;

import java.util.concurrent.CompletableFuture;

public interface AccountClient {
    CompletableFuture<ResponseExchangeWalletsDto> getWalletsType(ReqExchangeWalletsDto dto);

    void processTransferTransaction(ReqTransferMoneyDto dto);
}
