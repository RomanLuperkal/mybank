package org.ivanov.front.service.impl;

import lombok.RequiredArgsConstructor;
import org.ivanov.accountdto.wallet.CreateWalletDto;
import org.ivanov.accountdto.wallet.ResponseWalletDto;
import org.ivanov.front.client.AccountClient;
import org.ivanov.front.service.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final AccountClient client;

    @Override
    public ResponseWalletDto createWallet(Long accountId, CreateWalletDto createWalletDto, Set<ResponseWalletDto> wallets) {
        boolean isValidWallet = wallets.stream().noneMatch(w -> w.walletType().equals(createWalletDto.walletType()));
        if (!isValidWallet) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Счет этого типа уже существует");
        }
        return client.createWallet(accountId, createWalletDto);
    }

    @Override
    public void deleteWallet(Long accountId, Long walletId, Set<ResponseWalletDto> wallets) {
        boolean isValidWallet = wallets.stream().noneMatch(w -> w.balance().compareTo(BigDecimal.ZERO) == 0);
        if (!isValidWallet) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Нельзя удалить ненулевой счет");
        }
        client.deleteWallet(accountId, walletId);
    }
}
