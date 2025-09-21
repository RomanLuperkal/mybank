package org.ivanov.transfer.configuration.opentelemetry;

import com.zaxxer.hikari.HikariDataSource;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.instrumentation.jdbc.datasource.OpenTelemetryDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(OpenTelemetryProperties.class)
@RequiredArgsConstructor
public class OpenTelemetryConfig {
    private final OpenTelemetryProperties properties;

    @Bean
    public DataSource dataSource(OpenTelemetry openTelemetry) {
        HikariDataSource originalDataSource = new HikariDataSource();
        originalDataSource.setJdbcUrl(properties.getUrl());
        originalDataSource.setUsername(properties.getUsername());
        originalDataSource.setPassword(properties.getPassword());

        return new OpenTelemetryDataSource(originalDataSource, openTelemetry);
    }
}
