package org.ivanov.front.client.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ivanov.accountdto.account.CreateAccountDto;
import org.ivanov.accountdto.account.ResponseAccountDto;
import org.ivanov.accountdto.wallet.CreateWalletDto;
import org.ivanov.accountdto.wallet.ResponseWalletDto;
import org.ivanov.front.client.WalletClient;
import org.ivanov.front.handler.exception.AccountException;
import org.ivanov.front.handler.exception.RegistrationException;
import org.ivanov.front.handler.response.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalletClientImpl implements WalletClient {
    private final WebClient client;
    private final OAuth2AuthorizedClientManager clientManager;

    @Override
    @CircuitBreaker(name = "front-circuitbreaker", fallbackMethod = "fallbackCreateWallet")
    public ResponseWalletDto createWallet(CreateWalletDto dto) {
        return client.post()
                .uri("http://gateway/wallet")
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
                .bodyToMono(ResponseWalletDto.class)
                .retry(3)
                .block();
    }

    private String getAccessToken() {
        OAuth2AuthorizedClient system = clientManager.authorize(OAuth2AuthorizeRequest
                .withClientRegistrationId("account-client")
                .principal("system").build());
        return system.getAccessToken().getTokenValue();
    }

    private ResponseWalletDto fallbackCreateWallet(RuntimeException e) {
        final String message  = "Аккаунт сервис недоступен";
        var stubDto = new CreateAccountDto(null, null, null, null, null, null);
        log.info("execute fallbackRegistration: {}", e.getMessage());
        handleCircuitBreakerFailure(e, List.of(AccountException.class, RegistrationException.class), ResponseAccountDto.class);
        throw new AccountException(message, HttpStatus.SERVICE_UNAVAILABLE.toString());
    }

    private <T> void handleCircuitBreakerFailure(RuntimeException e, List<Class<? extends RuntimeException>> exceptions, Class<T> clazz) {
        for (Class<? extends RuntimeException> exceptionClass : exceptions) {
            if (exceptionClass.isInstance(e)) {
                throw e;
            }
        }
    }
}
