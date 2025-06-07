package org.ivanov.transfer.mapper;

import org.blog.blockdto.block.UnvalidatedTransactionDto;
import org.ivanov.transfer.model.Transaction;
import org.ivanov.transferdto.innertransferdto.InnerTransferReqDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    Transaction mapToTransaction(InnerTransferReqDto dto);

    UnvalidatedTransactionDto mapToUnvalidatedTransaction(Transaction transaction);
}
