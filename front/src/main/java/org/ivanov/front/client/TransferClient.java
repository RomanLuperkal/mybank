package org.ivanov.front.client;

import org.ivanov.transferdto.ResponseTransferDto;
import org.ivanov.transferdto.innertransferdto.InnerTransferReqDto;

public interface TransferClient {
    ResponseTransferDto createInnerTransfer(InnerTransferReqDto dto);
}
