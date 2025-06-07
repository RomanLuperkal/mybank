package org.ivanov.front.service.impl;

import lombok.RequiredArgsConstructor;
import org.ivanov.front.client.TransferClient;
import org.ivanov.front.service.TransferService;
import org.ivanov.transferdto.ResponseTransferDto;
import org.ivanov.transferdto.innertransferdto.InnerTransferReqDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
    private final TransferClient client;

    @Override
    public ResponseTransferDto createInnerTransfer(InnerTransferReqDto dto) {
        return client.createInnerTransfer(dto);
    }
}
