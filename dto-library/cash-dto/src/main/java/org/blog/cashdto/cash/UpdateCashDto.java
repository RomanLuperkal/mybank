package org.blog.cashdto.cash;

import java.math.BigDecimal;

public record UpdateCashDto(BigDecimal balance, Operation operation) {
    public enum Operation {
        ADD, REMOVE
    }
}
