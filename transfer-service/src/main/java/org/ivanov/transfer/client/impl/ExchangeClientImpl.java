package org.ivanov.transfer.client.impl;

import org.ivanov.exchangedto.currency.ResponseCurrencyDto;
import org.ivanov.transfer.client.ExchangeClient;
import org.ivanov.transfer.client.KeycloakManageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;

@Component
public class ExchangeClientImpl implements ExchangeClient {
    @Qualifier("service-client")
    @Autowired
    private WebClient client;
    @Autowired
    private KeycloakManageClient keycloakManageClient;
    @Value("${exchange-service.host}")
    private String exchangeServiceHost;


    @Override
    public CompletableFuture<ResponseCurrencyDto> getExchange() {
        return client.get()
                .uri(exchangeServiceHost + "/exchange")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + keycloakManageClient.getAccessToken())
                .retrieve()
                .bodyToMono(ResponseCurrencyDto.class)
                //.retry(3)
                .toFuture();
    }
}
