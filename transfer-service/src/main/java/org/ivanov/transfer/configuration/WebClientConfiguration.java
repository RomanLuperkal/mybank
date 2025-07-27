package org.ivanov.transfer.configuration;

import io.netty.channel.ChannelOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class WebClientConfiguration {
    @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}")
    private String KEYCLOAK_HOST_NAME = "";

    @Bean("service-client")
    public WebClient notificationClient() {
        return WebClient.builder().
                clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create()
                                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                                .responseTimeout(Duration.ofSeconds(30))))
                .build();
    }

    @Bean("keycloak-client")
    public WebClient keycloakClient() {
        return WebClient.builder().
                baseUrl(KEYCLOAK_HOST_NAME + "/protocol/openid-connect/token").
                clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create().responseTimeout(Duration.ofSeconds(30))))
                .build();
    }
}
