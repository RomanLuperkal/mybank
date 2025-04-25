package org.ivanov.account.mapper;

import org.ivanov.account.dto.account.CreateAccountDto;
import org.ivanov.account.dto.account.ResponseAccountDto;
import org.ivanov.account.model.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account mapToAccount(CreateAccountDto account);

    ResponseAccountDto mapToResponseAccount(Account account);
}
