package org.ivanov.exchangedto.currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateCurrencyDto(BigDecimal usd, BigDecimal cny, LocalDateTime timestamp) {}