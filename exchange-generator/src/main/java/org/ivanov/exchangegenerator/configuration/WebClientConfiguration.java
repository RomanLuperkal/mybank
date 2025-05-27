package org.ivanov.exchangegenerator.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class WebClientConfiguration {
    @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}")
    private String KEYCLOAK_HOST_NAME;

    @Bean("exchange-service-client")
    @LoadBalanced
    public WebClient exchangeServiceClient(ReactorLoadBalancerExchangeFilterFunction lbFunction) {
        return WebClient.builder().
                filter(lbFunction).
                clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create().responseTimeout(Duration.ofSeconds(30))))
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
