package org.ivanov.accountdto.wallet;

import java.math.BigDecimal;

public record ResponseWalletDto(Long walletId, String walletType, BigDecimal balance) {
}
