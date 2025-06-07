package org.ivanov.front.service;

import org.ivanov.transferdto.ResponseTransferDto;
import org.ivanov.transferdto.innertransferdto.InnerTransferReqDto;

public interface TransferService {
    ResponseTransferDto createInnerTransfer(InnerTransferReqDto dto);
}
