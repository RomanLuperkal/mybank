package org.ivanov.front.service.impl;

import lombok.RequiredArgsConstructor;
import org.blog.cashdto.cash.ResponseCashDto;
import org.blog.cashdto.cash.UpdateCashDto;
import org.ivanov.front.client.CashClient;
import org.ivanov.front.service.CashService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashServiceImpl implements CashService {
    private final CashClient client;
    @Override
    public ResponseCashDto updateCash(UpdateCashDto updateCashDto) {
        return client.updateCash(updateCashDto);
    }
}
