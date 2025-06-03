package org.ivanov.front.service;

import org.blog.cashdto.cash.ResponseCashDto;
import org.blog.cashdto.cash.UpdateCashDto;
import org.ivanov.accountdto.account.ResponseAccountDto;

public interface CashService {
    ResponseCashDto updateCash(UpdateCashDto updateCashDto, ResponseAccountDto userDetails);
}
