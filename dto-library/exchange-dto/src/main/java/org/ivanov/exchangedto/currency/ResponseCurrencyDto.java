package org.ivanov.exchangedto.currency;

import java.math.BigDecimal;

public record ResponseCurrencyDto (BigDecimal usd, BigDecimal cny) {}