package org.blog.blockservice.service.impl;

import org.blog.blockdto.block.ResponseValidatedTransactionDto;
import org.blog.blockdto.block.UnvalidatedTransactionDto;
import org.blog.blockservice.service.BlockerService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
//TODO Закрыть эндпоинты
public class BlockerServiceImpl implements BlockerService {

    @Override
    public ResponseValidatedTransactionDto validateTransaction(UnvalidatedTransactionDto dto) {
        BigDecimal amount = dto.amount();
        if (isValid(amount)) {
            return new ResponseValidatedTransactionDto("BLOCKED");
        } else {
            return new ResponseValidatedTransactionDto("APPROVED");
        }
    }

    private Boolean isValid(BigDecimal amount) {
        BigDecimal etalon = new BigDecimal(1_000_000);
        return amount.compareTo(etalon) > 0;
    }
}
