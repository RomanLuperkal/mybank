package org.blog.cashdto.transaction;

import java.math.BigDecimal;

public record ApprovedTransactionDto(BigDecimal amount, String transactionType, String email) {
}
