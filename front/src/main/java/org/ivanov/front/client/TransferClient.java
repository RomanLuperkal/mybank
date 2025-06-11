package org.ivanov.front.client;

import org.ivanov.transferdto.ResponseTransferDto;
import org.ivanov.transferdto.TransferReqDto;

public interface TransferClient {
    ResponseTransferDto createInnerTransfer(TransferReqDto dto);
}
