package org.blog.cashservice.service;

import org.blog.cashdto.cash.ResponseCashDto;
import org.blog.cashdto.cash.UpdateCashDto;

public interface CashService {
    ResponseCashDto createTransaction(UpdateCashDto dto);

    void validateTransaction();

    void processApprovedTransaction();
}
