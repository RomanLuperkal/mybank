package org.ivanov.front.service.impl;

import lombok.RequiredArgsConstructor;
import org.ivanov.accountdto.wallet.CreateWalletDto;
import org.ivanov.accountdto.wallet.ResponseWalletDto;
import org.ivanov.front.client.AccountClient;
import org.ivanov.front.service.WalletService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final AccountClient client;

    @Override
    public ResponseWalletDto createWallet(Long accountId, CreateWalletDto createWalletDto) {
        return client.createWallet(accountId, createWalletDto);
    }

    @Override
    public void deleteWallet(Long accountId, Long walletId) {
        client.deleteWallet(accountId, walletId);
    }
}
