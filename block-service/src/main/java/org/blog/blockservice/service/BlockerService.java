package org.blog.blockservice.service;

import org.blog.blockdto.block.ResponseValidatedTransactionDto;
import org.blog.blockdto.block.UnvalidatedTransactionDto;

public interface BlockerService {
    ResponseValidatedTransactionDto validateTransaction(UnvalidatedTransactionDto dto);
}
