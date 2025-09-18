package org.ivanov.transferdto;

import java.math.BigDecimal;

public record ExternalTransferDto(Long sourceWalletId,
                                  String username,
                                  BigDecimal amount,
                                  String email,
                                  String walletType,
                                  String login) {
}
