package org.ivanov.exchangeservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "currency_rates")
public class CurrencyRates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long currencyId;
    @Enumerated(EnumType.STRING)
    @Column(name = "currency_name")
    private Currencies currencyName;
    private BigDecimal value;
    private LocalDateTime timestamp;

    public enum Currencies {
        USD, CNY
    }
}
