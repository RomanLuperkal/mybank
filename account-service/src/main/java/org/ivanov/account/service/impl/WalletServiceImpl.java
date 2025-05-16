package org.ivanov.account.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final AccountRepository accountRepository;
    private final WalletMapper walletMapper;

    @Override
    public ResponseWalletDto createWallet(Long accountId, CreateWalletDto dto) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountException(HttpStatus.CONFLICT, "Аккаунта с id= " + accountId + " не существует."));
        Wallet wallet = walletMapper.mapToWallet(dto);
        wallet.setAccount(account);
        account.getWallets().add(wallet);

        return walletMapper.mapToResponseWalletDto(walletRepository.save(wallet));
    }

    @Override
    @Transactional
    public ResponseWalletDto deleteWallet(Long accountId, Long walletId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountException(HttpStatus.CONFLICT, "Аккаунта с id= " + accountId + " не существует."));

        Optional<Wallet> wallet = account.getWallets().stream().filter(w -> w.getWalletId().equals(walletId)).findFirst();
        wallet.ifPresentOrElse(w -> account.getWallets().remove(w), () -> {
            throw new AccountException(HttpStatus.CONFLICT, "");
        });
        accountRepository.save(account);
        return walletMapper.mapToResponseWalletDto(wallet.get());
    }
}
