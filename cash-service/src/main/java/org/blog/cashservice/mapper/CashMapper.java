package org.blog.cashservice.mapper;

import org.blog.cashdto.cash.UpdateCashDto;
import org.blog.cashservice.model.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CashMapper {
    Transaction mapToTransaction(UpdateCashDto dto);
}
