package org.ivanov.front.service.impl;

import lombok.RequiredArgsConstructor;
import org.ivanov.accountdto.wallet.ReqWalletInfoDto;
import org.ivanov.accountdto.wallet.ResponseWalletDto;
import org.ivanov.front.client.AccountClient;
import org.ivanov.front.client.TransferClient;
import org.ivanov.front.handler.exception.TransferException;
import org.ivanov.front.service.TransferService;
import org.ivanov.transferdto.ExternalTransferDto;
import org.ivanov.transferdto.ResponseTransferDto;
import org.ivanov.transferdto.TransferReqDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
    private final TransferClient transferClient;
    private final AccountClient accountClient;

    @Override
    public ResponseTransferDto createInnerTransfer(TransferReqDto dto, Set<ResponseWalletDto> wallets) {
        if (!validateTransfer(dto, wallets)) {
            return new ResponseTransferDto("Сумма перевода не может быть больше баланса на счете");
        }

        return transferClient.createInnerTransfer(dto);
    }

    @Override
    public ResponseTransferDto createExternalTransfer(ExternalTransferDto dto, Set<ResponseWalletDto> wallets) {
        if (!validateTransfer(dto, wallets)) {
            return new ResponseTransferDto("Сумма перевода не может быть больше баланса на счете");
        }
        Set<ResponseWalletDto> userWallets = accountClient.getWalletInfoByUsername(new ReqWalletInfoDto(dto.username()));
        var targetWallet = targetWalletTypeExists(userWallets, dto.walletType())
                .orElseThrow(() -> new TransferException("Такого типа кошелька у пользователя не существует", HttpStatus.NOT_FOUND));

        return transferClient.createInnerTransfer(new TransferReqDto(dto.sourceWalletId(), targetWallet.walletId(), dto.amount(), dto.email()));
    }

    private Boolean validateTransfer(TransferReqDto dto, Set<ResponseWalletDto> wallets) {
        return wallets.stream().filter(w -> w.walletId().equals(dto.getSourceWalletId())).anyMatch(w -> w.balance().compareTo(dto.getAmount()) >= 0);
    }

    private Boolean validateTransfer(ExternalTransferDto dto, Set<ResponseWalletDto> wallets) {
        return wallets.stream().filter(w -> w.walletId().equals(dto.sourceWalletId())).anyMatch(w -> w.balance().compareTo(dto.amount()) >= 0);
    }

    private Optional<ResponseWalletDto> targetWalletTypeExists(Set<ResponseWalletDto> wallets, String targetWalletType) {
        return wallets.stream().filter(w -> w.walletType().equals(targetWalletType)).findFirst();
    }
}
