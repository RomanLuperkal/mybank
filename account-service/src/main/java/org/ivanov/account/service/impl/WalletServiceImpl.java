package org.ivanov.account.service.impl;

import lombok.RequiredArgsConstructor;
import org.ivanov.account.mapper.WalletMapper;
import org.ivanov.account.model.Wallet;
import org.ivanov.account.repository.WalletRepository;
import org.ivanov.account.service.WalletService;
import org.ivanov.accountdto.wallet.CreateWalletDto;
import org.ivanov.accountdto.wallet.ResponseWalletDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;

    @Override
    public ResponseWalletDto createWallet(CreateWalletDto dto) {
        Wallet wallet = walletMapper.mapToWallet(dto);

        return walletMapper.mapToResponseWalletDto(walletRepository.save(wallet));
    }
}
