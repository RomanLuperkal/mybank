package org.ivanov.account.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blog.cashdto.transaction.ApprovedTransactionDto;
import org.ivanov.account.handler.exception.AccountException;
import org.ivanov.account.mapper.WalletMapper;
import org.ivanov.account.model.Account;
import org.ivanov.account.model.NotificationOutBox;
import org.ivanov.account.model.Wallet;
import org.ivanov.account.repository.AccountRepository;
import org.ivanov.account.repository.WalletRepository;
import org.ivanov.account.service.NotificationOutBoxService;
import org.ivanov.account.service.WalletService;
import org.ivanov.accountdto.wallet.CreateWalletDto;
import org.ivanov.accountdto.wallet.ResponseWalletDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasAuthority('ACCOUNT_ROLE')")
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final AccountRepository accountRepository;
    private final WalletMapper walletMapper;
    private final NotificationOutBoxService notificationOutBoxService;

    @Override
    @Transactional
    public ResponseWalletDto createWallet(Long accountId, CreateWalletDto dto) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountException(HttpStatus.CONFLICT, "Аккаунта с id= " + accountId + " не существует."));
        Wallet wallet = walletMapper.mapToWallet(dto);
        wallet.setAccount(account);
        Wallet savedWallet = walletRepository.save(wallet);
        NotificationOutBox prepareMessage = notificationOutBoxService
                .createNotificationOutBoxMessage("Создание нового счета",
                        savedWallet.getWalletType() + " счет успешно создан", account.getEmail());
        notificationOutBoxService.createNotificationOutBoxMessage(prepareMessage);

        return walletMapper.mapToResponseWalletDto(savedWallet);
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

        NotificationOutBox prepareMessage = notificationOutBoxService
                .createNotificationOutBoxMessage("Удаление счета",
                        wallet.get().getWalletType() + " счет успешно удален", account.getEmail());
        notificationOutBoxService.createNotificationOutBoxMessage(prepareMessage);
        return walletMapper.mapToResponseWalletDto(wallet.get());
    }

    @Override
    public void updateWallet(Long accountId, Long walletId, ApprovedTransactionDto dto) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountException(HttpStatus.CONFLICT, "Аккаунта с id= " + accountId + " не существует."));
        Wallet wallet = account.getWallets().stream().filter(w -> w.getWalletId().equals(walletId)).findFirst()
                .orElseThrow(() -> new AccountException(HttpStatus.CONFLICT, "Такого счета не существует"));
        String operation = dto.transactionType();
        NotificationOutBox prepareMessage;
        switch (operation) {
            case "ADD":
                wallet.setBalance(wallet.getBalance().add(dto.amount()));
                prepareMessage = notificationOutBoxService
                        .createNotificationOutBoxMessage("Операции со счетом",
                                "Счет успешно пополнен на " + dto.amount() + " " + wallet.getWalletType(), account.getEmail());
                notificationOutBoxService.createNotificationOutBoxMessage(prepareMessage);
                break;
            case "REMOVE":
                BigDecimal currentBalance = wallet.getBalance();
                if (currentBalance.compareTo(dto.amount()) < 0) {
                    prepareMessage = notificationOutBoxService
                            .createNotificationOutBoxMessage("Операции со счетом",
                                    "На счете недостаточно средств", account.getEmail());
                    notificationOutBoxService.createNotificationOutBoxMessage(prepareMessage);
                } else {
                    prepareMessage = notificationOutBoxService
                            .createNotificationOutBoxMessage("Операции со счетом",
                                    "С счета успешно снято" + dto.amount() + " " + wallet.getWalletType(), account.getEmail());
                    notificationOutBoxService.createNotificationOutBoxMessage(prepareMessage);
                }
                wallet.setBalance(wallet.getBalance().subtract(dto.amount()));
        }
        walletRepository.save(wallet);
    }
}
