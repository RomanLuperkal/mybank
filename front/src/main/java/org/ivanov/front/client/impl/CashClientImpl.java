package org.ivanov.front.client.impl;

import lombok.RequiredArgsConstructor;
import org.blog.cashdto.cash.ResponseCashDto;
import org.blog.cashdto.cash.UpdateCashDto;
import org.ivanov.front.client.CashClient;
import org.ivanov.front.handler.exception.AccountException;
import org.ivanov.front.handler.response.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CashClientImpl implements CashClient {
    private final WebClient client;
    private final OAuth2AuthorizedClientManager clientManager;

    @Override
    public ResponseCashDto updateCash(UpdateCashDto dto) {
        return client.patch()
                .uri("http://gateway/cash")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .bodyValue(dto).retrieve()
                .onStatus(status -> status == HttpStatus.CONFLICT,
                        response -> response.bodyToMono(ApiError.class)
                                .flatMap(body -> Mono.error(new AccountException(body.getMessage(), HttpStatus.CONFLICT.toString()))))
                .onStatus(status -> status == HttpStatus.FORBIDDEN,
                        response -> Mono.error(new AccountException("403 Forbidden from GET account service", HttpStatus.FORBIDDEN.toString())))
                .onStatus(status -> status == HttpStatus.GATEWAY_TIMEOUT,
                        response -> response.bodyToMono(ApiError.class)
                                .flatMap(body -> Mono.error(new AccountException(body.getMessage(), HttpStatus.GATEWAY_TIMEOUT.toString()))))
                .bodyToMono(ResponseCashDto.class)
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
