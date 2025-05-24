package org.ivanov.exchangegenerator.service.impl;

import org.ivanov.exchangegenerator.service.CurrencyService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final Random random = new Random();
    private final BigDecimal MIN = new BigDecimal("80");
    private final BigDecimal MAX = new BigDecimal("110");
    @Override
    public BigDecimal getCurrency() {
        BigDecimal range = MAX.subtract(MIN);

        BigDecimal randomValue = BigDecimal.valueOf(random.nextDouble());

        BigDecimal result = MIN.add(range.multiply(randomValue));

        return result.setScale(2, RoundingMode.HALF_UP);
    }
}
