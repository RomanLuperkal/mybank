package org.ivanov.transferdto;

import java.math.BigDecimal;

public record ReqTransferMoneyDto(Long sourceWalletId, BigDecimal sourceAmount, Long targetWalletId, BigDecimal targetAmount) {
}
