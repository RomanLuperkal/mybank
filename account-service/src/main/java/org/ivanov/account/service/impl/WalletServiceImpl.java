package org.ivanov.account.service.impl;

import lombok.RequiredArgsConstructor;
import org.ivanov.account.handler.exception.AccountException;
import org.ivanov.account.mapper.WalletMapper;
import org.ivanov.account.model.Account;
import org.ivanov.account.model.Wallet;
import org.ivanov.account.repository.AccountRepository;
import org.ivanov.account.repository.WalletRepository;
import org.ivanov.account.service.WalletService;
import org.ivanov.accountdto.wallet.CreateWalletDto;
import org.ivanov.accountdto.wallet.ResponseWalletDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final AccountRepository accountRepository;
    private final WalletMapper walletMapper;

    @Override
    public ResponseWalletDto createWallet(Long accountId, CreateWalletDto dto) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountException(HttpStatus.CONFLICT, "Аккаунта с id= " + accountId + " не существует."));
        Wallet wallet = walletMapper.mapToWallet(dto);
        account.getWallets().add(wallet);
        Account savedAccount = accountRepository.save(account);

        return walletMapper.mapToResponseWalletDto(wallet);
    }
}
