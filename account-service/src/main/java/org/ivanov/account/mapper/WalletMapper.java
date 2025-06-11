package org.ivanov.account.mapper;

import org.ivanov.account.model.Wallet;
import org.ivanov.accountdto.wallet.CreateWalletDto;
import org.ivanov.accountdto.wallet.ResponseWalletDto;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface WalletMapper {
    //@Mapping(target = "walletType", expression = "java(wallet.getWalletType().toString())")
    ResponseWalletDto mapToResponseWalletDto(Wallet wallet);

    Wallet mapToWallet(CreateWalletDto dto);

    Set<ResponseWalletDto> mapToSetWalletDto(Set<Wallet> wallets);

}
