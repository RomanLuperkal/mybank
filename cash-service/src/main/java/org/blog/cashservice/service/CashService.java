package org.blog.cashservice.service;

import org.blog.cashdto.cash.UpdateCashDto;

public interface CashService {
    void createTransaction(UpdateCashDto dto);
}
