package org.ivanov.exchangeservice.provider;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Getter
@Setter
public class ExchangeRateProvider {
    private BigDecimal USD;
    private BigDecimal CNY;
}
