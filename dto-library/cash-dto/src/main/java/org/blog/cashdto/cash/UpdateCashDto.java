package org.blog.cashdto.cash;

import java.math.BigDecimal;

public record UpdateCashDto(Long accountId, Long walletId, BigDecimal amount, String email, String transactionType) {
}
