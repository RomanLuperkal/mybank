package org.ivanov.transfer.configuration.opentelemetry;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.datasource")
@RequiredArgsConstructor
@Getter
@Setter
public class OpenTelemetryProperties {
    private String url;
    private String username;
    private String password;
}
