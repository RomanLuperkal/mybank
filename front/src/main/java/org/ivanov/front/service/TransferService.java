package org.ivanov.front.service;

import org.ivanov.accountdto.wallet.ResponseWalletDto;
import org.ivanov.transferdto.ExternalTransferDto;
import org.ivanov.transferdto.ResponseTransferDto;
import org.ivanov.transferdto.TransferReqDto;

import java.util.Set;

public interface TransferService {
    ResponseTransferDto createInnerTransfer(TransferReqDto dto, Set<ResponseWalletDto> wallets);

    ResponseTransferDto createExternalTransfer(ExternalTransferDto dto, Set<ResponseWalletDto> wallets);
}
