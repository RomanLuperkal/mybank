package org.ivanov.transfer.service.impl;

import lombok.RequiredArgsConstructor;
import org.blog.blockdto.block.ResponseValidatedTransactionDto;
import org.blog.blockdto.block.UnvalidatedTransactionDto;
import org.ivanov.accountdto.wallet.ResponseWalletDto;
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
import org.ivanov.transferdto.ResponseTransferDto;
import org.ivanov.transferdto.innertransferdto.InnerTransferReqDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
    public void processApprovedTransaction() {
        Optional<Transaction> tr = transactionRepository.findFirstByStatus(Transaction.Status.APPROVED);
        tr.ifPresent(transaction -> {
            ResponseCurrencyDto exchange = exchangeClient.getExchange();
            ResponseWalletDto targetWallet = accountClient.getWallet(transaction.getTargetWalletId());
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
}
