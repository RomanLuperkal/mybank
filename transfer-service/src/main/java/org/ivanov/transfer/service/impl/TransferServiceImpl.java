package org.ivanov.transfer.service.impl;

import lombok.RequiredArgsConstructor;
import org.blog.blockdto.block.ResponseValidatedTransactionDto;
import org.blog.blockdto.block.UnvalidatedTransactionDto;
import org.ivanov.accountdto.wallet.ResponseExchangeWalletsDto;
import org.ivanov.exchangedto.currency.ResponseCurrencyDto;
import org.ivanov.transfer.client.AccountClient;
import org.ivanov.transfer.client.BlockClient;
import org.ivanov.transfer.client.ExchangeClient;
import org.ivanov.transfer.mapper.TransactionMapper;
import org.ivanov.transfer.model.NotificationOutBox;
import org.ivanov.transfer.model.Transaction;
import org.ivanov.transfer.repository.TransactionRepository;
import org.ivanov.transfer.service.NotificationOutBoxService;
import org.ivanov.transfer.service.TransferService;
import org.ivanov.transferdto.ReqExchangeWalletsDto;
import org.ivanov.transferdto.ReqTransferMoneyDto;
import org.ivanov.transferdto.ResponseTransferDto;
import org.ivanov.transferdto.innertransferdto.InnerTransferReqDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final BlockClient blockClient;
    private final ExchangeClient exchangeClient;
    private final AccountClient accountClient;
    private final NotificationOutBoxService notificationOutBoxService;

    @Override
    @PreAuthorize("hasAuthority('TRANSFER_ROLE')")
    public ResponseTransferDto createInnerTransfer(InnerTransferReqDto dto) {
        Transaction transaction = transactionMapper.mapToTransaction(dto);
        transactionRepository.save(transaction);
        return new ResponseTransferDto("Ваш перевод отправлен на обработку");
    }

    @Override
    @Transactional
    public void validateTransaction() {
        Optional<Transaction> tr = transactionRepository.findFirstByStatus(Transaction.Status.WAITING);
        tr.ifPresent(transaction -> {
            UnvalidatedTransactionDto unvalidatedTransaction = transactionMapper.mapToUnvalidatedTransaction(transaction);
            ResponseValidatedTransactionDto validatedTransaction = blockClient.validateTransaction(unvalidatedTransaction);
            Transaction.Status status = Transaction.Status.valueOf(validatedTransaction.valid());
            switch (status) {
                case APPROVED -> saveValidationResult(status, transaction);
                case BLOCKED -> {
                    saveValidationResult(status, transaction);
                    prepareMessageForNotification(transaction);
                }
            }
        });
    }

    @Override
    @Transactional
    public void processApprovedTransaction() {
        Optional<Transaction> tr = transactionRepository.findFirstByStatus(Transaction.Status.APPROVED);
        tr.ifPresent(transaction -> {
            CompletableFuture<ResponseCurrencyDto> exchanges = exchangeClient.getExchange();
            CompletableFuture<ResponseExchangeWalletsDto> walletsType = accountClient
                    .getWalletsType(new ReqExchangeWalletsDto(transaction.getSourceWalletId(), transaction.getTargetWalletId()));
            CompletableFuture<BigDecimal> targetAmount = exchanges.thenCombine(walletsType, (res1, res2) -> {
                var currencyMap = getCurrencyMap(res1);
                return conversionOperations(transaction.getAmount(), res2.sourceWallet(),
                        res2.targetWallet(), currencyMap);
            });

        accountClient.processTransferTransaction(prepeareReqTransferMoneyDto(transaction.getSourceWalletId(), transaction.getAmount(),
                transaction.getTargetWalletId(), targetAmount.join()));
        transaction.setStatus(Transaction.Status.DONE);
        });
    }

    private void saveValidationResult(Transaction.Status status, Transaction transaction) {
        transaction.setStatus(status);
        transactionRepository.save(transaction);
    }

    private void prepareMessageForNotification(Transaction transaction) {
        NotificationOutBox message = notificationOutBoxService.prepareNotificationOutBoxMessage("Операции с личными счетами",
                "Перевод " + transaction.getAmount() + "Заблокирован ",
                transaction.getEmail());
        notificationOutBoxService.createNotificationOutBoxMessage(message);
    }

    private static BigDecimal conversionOperations(BigDecimal amount, ResponseExchangeWalletsDto.WalletType sourceWallet,
                                                   ResponseExchangeWalletsDto.WalletType targetWallet,
                                                   Map<ResponseExchangeWalletsDto.WalletType, BigDecimal> currencies ) {
        BigDecimal amountInRub = amount.multiply(currencies.get(sourceWallet));

        return amountInRub
                .divide(currencies.get(targetWallet), 2, RoundingMode.HALF_UP);
    }

    private static Map<ResponseExchangeWalletsDto.WalletType, BigDecimal> getCurrencyMap(ResponseCurrencyDto dto) {
        Map< ResponseExchangeWalletsDto.WalletType, BigDecimal> currencyMap = new HashMap<>();
        currencyMap.put(ResponseExchangeWalletsDto.WalletType.RUB, BigDecimal.ONE);
        currencyMap.put(ResponseExchangeWalletsDto.WalletType.USD, dto.usd());
        currencyMap.put(ResponseExchangeWalletsDto.WalletType.CNY, dto.cny());
        return currencyMap;
    }

    private ReqTransferMoneyDto prepeareReqTransferMoneyDto(Long sourceWalletId, BigDecimal sourceAmount,
                                                            Long targetWalletId, BigDecimal targetAmount) {
        return new ReqTransferMoneyDto(sourceWalletId, sourceAmount, targetWalletId, targetAmount);
    }
}
