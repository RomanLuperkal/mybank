package org.ivanov.front.service;

import org.ivanov.accountdto.wallet.ResponseWalletDto;
import org.ivanov.transferdto.ResponseTransferDto;
import org.ivanov.transferdto.innertransferdto.InnerTransferReqDto;

import java.util.Set;

public interface TransferService {
    ResponseTransferDto createInnerTransfer(InnerTransferReqDto dto, Set<ResponseWalletDto> wallets);
}
