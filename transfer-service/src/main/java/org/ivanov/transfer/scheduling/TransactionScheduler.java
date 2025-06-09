package org.ivanov.transfer.scheduling;

import lombok.RequiredArgsConstructor;
import org.ivanov.transfer.service.TransferService;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
public class TransactionScheduler {
    private final TransferService transferService;

    @Scheduled(fixedRate = 5000)
    public void validateTransaction() {
        transferService.validateTransaction();
    }

    @Scheduled(fixedRate = 5000)
    public void processTransaction() {
        transferService.processApprovedTransaction();
    }
}
