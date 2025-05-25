package org.ivanov.exchangegenerator.client.impl;

import lombok.RequiredArgsConstructor;
import org.ivanov.exchangedto.currency.CreateCurrencyDto;
import org.ivanov.exchangegenerator.client.ExchangeClient;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class ExchangeClientImpl implements ExchangeClient {

    private final OAuth2AuthorizedClientManager clientManager;
    private final WebClient client;

    @Override
    public void changeExchange(CreateCurrencyDto dto) {
        client.post()
                .uri("http://gateway/exchange")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .bodyValue(dto).retrieve()
                .bodyToMono(Void.class)
                //.retry(3)
                .block();
    }

    private String getAccessToken() {
        OAuth2AuthorizedClient system = clientManager.authorize(OAuth2AuthorizeRequest
                .withClientRegistrationId("account-client")
                .principal("system").build());
        return system.getAccessToken().getTokenValue();
    }
}
