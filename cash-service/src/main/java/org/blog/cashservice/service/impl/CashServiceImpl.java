package org.blog.cashservice.service.impl;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.blog.blockdto.block.ResponseValidatedTransactionDto;
import org.blog.blockdto.block.UnvalidatedTransactionDto;
import org.blog.cashdto.cash.ResponseCashDto;
import org.blog.cashdto.cash.UpdateCashDto;
import org.blog.cashdto.transaction.ApprovedTransactionDto;
import org.blog.cashservice.client.AccountClient;
import org.blog.cashservice.client.BlockClient;
import org.blog.cashservice.mapper.CashMapper;
import org.blog.cashservice.model.NotificationOutBox;
import org.blog.cashservice.model.Transaction;
import org.blog.cashservice.repository.CashRepository;
import org.blog.cashservice.service.CashService;
import org.blog.cashservice.service.NotificationOutBoxService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CashServiceImpl implements CashService {
    private final CashRepository cashRepository;
    private final CashMapper cashMapper;
    private final BlockClient blockClient;
    private final AccountClient accountClient;
    private final NotificationOutBoxService notificationOutBoxService;
    private final MeterRegistry meterRegistry;

    @Override
    @PreAuthorize("hasAuthority('CASH_ROLE')")
    public ResponseCashDto createTransaction(UpdateCashDto dto) {
        Transaction newTransaction = cashMapper.mapToTransaction(dto);
        cashRepository.save(newTransaction);
        return prepareResponse(newTransaction.getTransactionType());
    }

    @Override
    @Transactional
    public void validateTransaction() {
        Optional<Transaction> tr = cashRepository.findFirstByStatus(Transaction.Status.WAITING);
        tr.ifPresent(transaction -> {
            UnvalidatedTransactionDto unvalidatedTransaction = cashMapper.mapToUnvalidatedTransaction(transaction);
            ResponseValidatedTransactionDto validatedTransaction = blockClient.validateTransaction(unvalidatedTransaction);
            Transaction.Status status = Transaction.Status.valueOf(validatedTransaction.valid());
            switch (status) {
                case APPROVED -> saveValidationResult(status, transaction);
                case BLOCKED -> {
                    meterRegistry.counter("blocked_transaction", "login", transaction.getLogin(),
                            "source_id", transaction.getWalletId().toString(), "target_wallet_id", null);
                    saveValidationResult(status, transaction);
                    prepareMessageForNotification(transaction);
                }
            }
        });

    }

    @Override
    public void processApprovedTransaction() {
        Optional<Transaction> tr= cashRepository.findFirstByStatus(Transaction.Status.APPROVED);
        tr.ifPresent(transaction -> {
            ApprovedTransactionDto dto = cashMapper.mapToApprovedTransaction(transaction);
            accountClient.processTransaction(transaction.getAccountId(), transaction.getWalletId(), dto);
            transaction.setStatus(Transaction.Status.DONE);
            cashRepository.save(transaction);
        });

    }

    private ResponseCashDto prepareResponse(Transaction.TransactionType type) {
        return switch (type) {
            case ADD -> new ResponseCashDto("Зачисление средств отправлено на обработку");
            case REMOVE -> new ResponseCashDto("Cписание средств отправлено на обработку");
        };
    }

    private void saveValidationResult(Transaction.Status status, Transaction transaction) {
        transaction.setStatus(status);
        cashRepository.save(transaction);
    }

    private void prepareMessageForNotification(Transaction transaction) {
        NotificationOutBox message = notificationOutBoxService.prepareNotificationOutBoxMessage("Операции с личными счетами",
                transaction.getTransactionType().getMessage() + ": " + transaction.getAmount() + " заблокирована",
                transaction.getEmail());
        notificationOutBoxService.createNotificationOutBoxMessage(message);
    }
}
