package org.ivanov.front.service.impl;

import lombok.RequiredArgsConstructor;
import org.ivanov.accountdto.wallet.ResponseWalletDto;
import org.ivanov.front.client.TransferClient;
import org.ivanov.front.service.TransferService;
import org.ivanov.transferdto.ResponseTransferDto;
import org.ivanov.transferdto.innertransferdto.InnerTransferReqDto;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
    private final TransferClient client;

    @Override
    public ResponseTransferDto createInnerTransfer(InnerTransferReqDto dto, Set<ResponseWalletDto> wallets) {
        if (!validateTransfer(dto, wallets)) {
            return new ResponseTransferDto("Сумма перевода не может быть больше баланса на счете");
        }

        return client.createInnerTransfer(dto);
    }

    private Boolean validateTransfer(InnerTransferReqDto dto, Set<ResponseWalletDto> wallets) {
        return wallets.stream().filter(w -> w.walletId().equals(dto.getSourceWalletId())).anyMatch(w -> w.balance().compareTo(dto.getAmount()) > 0);
    }
}
