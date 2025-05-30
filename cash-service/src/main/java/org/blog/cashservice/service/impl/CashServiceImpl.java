package org.blog.cashservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.blog.blockdto.block.ResponseValidatedTransactionDto;
import org.blog.blockdto.block.UnvalidatedTransactionDto;
import org.blog.cashdto.cash.ResponseCashDto;
import org.blog.cashdto.cash.UpdateCashDto;
import org.blog.cashservice.client.BlockClient;
import org.blog.cashservice.mapper.CashMapper;
import org.blog.cashservice.model.Transaction;
import org.blog.cashservice.repository.CashRepository;
import org.blog.cashservice.service.CashService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
//TODO закрыть эндпоинты
public class CashServiceImpl implements CashService {
    private final CashRepository cashRepository;
    private final CashMapper cashMapper;
    private final BlockClient blockClient;

    @Override
    public ResponseCashDto createTransaction(UpdateCashDto dto) {
        Transaction newTransaction = cashMapper.mapToTransaction(dto);
        cashRepository.save(newTransaction);
        return prepareResponse(newTransaction.getTransactionType());
    }

    @Override
    public void validateTransaction(Transaction transaction) {
        UnvalidatedTransactionDto unvalidatedTransaction = cashMapper.mapToUnvalidatedTransaction(transaction);
        ResponseValidatedTransactionDto validatedTransaction = blockClient.validateTransaction(unvalidatedTransaction);
        //TODO проверить результат валидации и изменить статус\отправить уведомление в зависимости от результата

    }

    private ResponseCashDto prepareResponse(Transaction.TransactionType type) {
        return switch (type) {
            case ADD -> new ResponseCashDto("Зачисление средств отправлено на обработку");
            case REMOVE -> new ResponseCashDto("Cписание средств отправлено на обработку");
        };
    }
}
