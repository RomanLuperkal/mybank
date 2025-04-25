package org.ivanov.account.dto.account;

import lombok.Getter;
import lombok.Setter;
import org.ivanov.account.model.Wallet;

import java.util.Set;

@Getter
@Setter
public class ResponseAccountDto {
    private Long accountId;
    private Set<Wallet> wallets;
}
