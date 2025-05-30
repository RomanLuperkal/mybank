package org.blog.cashservice.service;

import org.blog.cashdto.cash.ResponseCashDto;
import org.blog.cashdto.cash.UpdateCashDto;
import org.blog.cashservice.model.Transaction;

public interface CashService {
    ResponseCashDto createTransaction(UpdateCashDto dto);

    void validateTransaction(Transaction transaction);
}
