package org.ivanov.exchangedto.currency;

import java.math.BigDecimal;

public record CreateCurrencyDto(BigDecimal usd, BigDecimal cny) {}