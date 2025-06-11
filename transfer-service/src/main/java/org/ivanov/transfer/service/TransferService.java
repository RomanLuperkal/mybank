package org.ivanov.transfer.service;

import org.ivanov.transferdto.ResponseTransferDto;
import org.ivanov.transferdto.TransferReqDto;

public interface TransferService {
    ResponseTransferDto createInnerTransfer(TransferReqDto dto);

    void validateTransaction();

    void processApprovedTransaction();
}
