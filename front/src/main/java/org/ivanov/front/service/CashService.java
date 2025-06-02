package org.ivanov.front.service;

import org.blog.cashdto.cash.ResponseCashDto;
import org.blog.cashdto.cash.UpdateCashDto;

public interface CashService {
    ResponseCashDto updateCash(UpdateCashDto updateCashDto);
}
