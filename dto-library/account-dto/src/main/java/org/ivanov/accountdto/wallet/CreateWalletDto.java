package org.ivanov.accountdto.wallet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateWalletDto(@NotBlank String walletType) {
}
