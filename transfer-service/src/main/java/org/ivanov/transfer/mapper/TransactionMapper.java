package org.ivanov.transfer.mapper;

import org.blog.blockdto.block.UnvalidatedTransactionDto;
import org.ivanov.transfer.model.Transaction;
import org.ivanov.transferdto.TransferReqDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    Transaction mapToTransaction(TransferReqDto dto);

    UnvalidatedTransactionDto mapToUnvalidatedTransaction(Transaction transaction);
}
