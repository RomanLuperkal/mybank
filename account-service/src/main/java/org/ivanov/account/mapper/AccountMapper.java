package org.ivanov.account.mapper;


import org.ivanov.account.model.Account;
import org.ivanov.accountdto.account.CreateAccountDto;
import org.ivanov.accountdto.account.ResponseAccountDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account mapToAccount(CreateAccountDto account);

    ResponseAccountDto mapToResponseAccount(Account account);
}
