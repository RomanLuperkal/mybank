package org.blog.cashservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.blog.cashdto.cash.UpdateCashDto;
import org.blog.cashservice.mapper.CashMapper;
import org.blog.cashservice.model.Transaction;
import org.blog.cashservice.repository.CashRepository;
import org.blog.cashservice.service.CashService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashServiceImpl implements CashService {
    private final CashRepository cashRepository;
    private final CashMapper cashMapper;

    @Override
    public void createTransaction(UpdateCashDto dto) {
        Transaction newTransaction = cashMapper.mapToTransaction(dto);
        cashRepository.save(newTransaction);
    }
}
