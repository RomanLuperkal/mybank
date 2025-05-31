package org.blog.cashservice.client;

import org.blog.cashdto.transaction.ApprovedTransactionDto;

public interface AccountClient {
    void processTransaction(Long accountId, Long walletId, ApprovedTransactionDto dto);
}
