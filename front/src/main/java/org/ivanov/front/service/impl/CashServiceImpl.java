package org.ivanov.front.service.impl;

import lombok.RequiredArgsConstructor;
import org.blog.cashdto.cash.ResponseCashDto;
import org.blog.cashdto.cash.UpdateCashDto;
import org.ivanov.accountdto.account.ResponseAccountDto;
import org.ivanov.front.client.CashClient;
import org.ivanov.front.service.CashService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashServiceImpl implements CashService {
    private final CashClient client;
    @Override
    public ResponseCashDto updateCash(UpdateCashDto updateCashDto, ResponseAccountDto userDetails) {
        if (updateCashDto.transactionType().equals("REMOVE")) {
            boolean isValidAmount = userDetails.wallets().stream()
                    .filter(w -> w.walletId().equals(updateCashDto.walletId())).noneMatch(w -> w.balance().compareTo(updateCashDto.amount()) < 0);
            if (!isValidAmount) {
                return new ResponseCashDto("Недостаточно средств");
            }
        }

        return client.updateCash(updateCashDto);
    }
}
