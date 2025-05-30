package org.blog.cashservice.client;

import org.blog.blockdto.block.ResponseValidatedTransactionDto;
import org.blog.blockdto.block.UnvalidatedTransactionDto;

public interface BlockClient {
    ResponseValidatedTransactionDto validateTransaction(UnvalidatedTransactionDto dto);
}
