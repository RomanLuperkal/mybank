package org.ivanov.exchangeservice.utils;

import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class ExchangeUpdateMetrics {

    private final MeterRegistry meterRegistry;

    private volatile Instant lastUpdateTime;

    @PostConstruct
    void init() {
        lastUpdateTime = Instant.now();
        meterRegistry.gauge("rate_update_seconds", this, metrics ->
                        Duration.between(metrics.lastUpdateTime, Instant.now()).getSeconds()
                );
    }


    public void rateUpdated() {
        this.lastUpdateTime = Instant.now();
    }

}
