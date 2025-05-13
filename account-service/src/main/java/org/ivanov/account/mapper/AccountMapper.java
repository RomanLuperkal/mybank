package org.ivanov.account.mapper;


import org.ivanov.account.model.Account;
import org.ivanov.accountdto.account.CreateAccountDto;
import org.ivanov.accountdto.account.ResponseAccountDto;
import org.ivanov.accountdto.account.ResponseAccountInfoDto;
import org.ivanov.accountdto.account.UpdateProfileDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account mapToAccount(CreateAccountDto account);

    ResponseAccountDto mapToResponseAccount(Account account);

    ResponseAccountInfoDto mapToResponseAccountInfoDto(Account account);

    Account mapToProduct(@MappingTarget Account account, UpdateProfileDto dto);
}
