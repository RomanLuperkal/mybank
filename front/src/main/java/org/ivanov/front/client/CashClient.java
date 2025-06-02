package org.ivanov.front.client;

import org.blog.cashdto.cash.ResponseCashDto;
import org.blog.cashdto.cash.UpdateCashDto;

public interface CashClient {
    ResponseCashDto updateCash(UpdateCashDto updateCashDto);
}
