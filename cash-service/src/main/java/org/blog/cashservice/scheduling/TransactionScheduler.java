package org.blog.cashservice.scheduling;

import lombok.RequiredArgsConstructor;
import org.blog.cashservice.service.CashService;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
public class TransactionScheduler {
    private final CashService cashService;

    @Scheduled(fixedRate = 5000)
    public void validateTransaction() {
        cashService.validateTransaction();
    }

    @Scheduled(fixedRate = 5000)
    public void processTransaction() {
        cashService.processApprovedTransaction();
    }
}
