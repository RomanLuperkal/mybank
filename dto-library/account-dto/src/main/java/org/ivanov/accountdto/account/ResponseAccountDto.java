package org.ivanov.accountdto.account;

import org.ivanov.accountdto.wallet.ResponseWalletDto;

import java.time.LocalDate;
import java.util.Set;

public record ResponseAccountDto(
        Long accountId,
        String username,
        String password,
        String role,
        Set<ResponseWalletDto> wallets,
        String firstName,
        String lastName,
        String email,
        LocalDate dateOfBirth
) {
}
