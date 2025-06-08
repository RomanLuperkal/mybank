package org.ivanov.transfer.service;

import org.ivanov.transferdto.ResponseTransferDto;
import org.ivanov.transferdto.innertransferdto.InnerTransferReqDto;

public interface TransferService {
    ResponseTransferDto createInnerTransfer(InnerTransferReqDto dto);

    void validateTransaction();

    void processApprovedTransaction();
}
