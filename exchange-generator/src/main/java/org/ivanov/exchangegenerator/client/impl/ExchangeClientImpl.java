package org.ivanov.exchangegenerator.client.impl;

import org.ivanov.exchangedto.currency.CreateCurrencyDto;
import org.ivanov.exchangegenerator.client.ExchangeClient;
import org.ivanov.exchangegenerator.client.KeycloakManageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ExchangeClientImpl implements ExchangeClient {

    @Qualifier("exchange-service-client")
    @Autowired
    private WebClient exchangeClient;
    @Autowired
    private KeycloakManageClient keycloakManageClient;
    @Value("${exchange-service.host}")
    private String exchangeServiceHost;



    @Override
    public void changeExchange(CreateCurrencyDto dto) {
        exchangeClient.patch()
                .uri(exchangeServiceHost + "/exchange/change")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + keycloakManageClient.getAccessToken())
                .bodyValue(dto).retrieve()
                .bodyToMono(Void.class)
                //.retry(3)
                .block();
    }


}

