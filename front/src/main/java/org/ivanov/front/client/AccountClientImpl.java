package org.ivanov.front.client;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ivanov.accountdto.account.CreateAccountDto;
import org.ivanov.accountdto.account.ResponseAccountDto;
import org.ivanov.accountdto.account.ResponseAccountInfoDto;
import org.ivanov.front.handler.exception.AccountException;
import org.ivanov.front.handler.exception.LoginException;
import org.ivanov.front.handler.response.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccountClientImpl implements AccountClient {
    private final WebClient client;
    private final OAuth2AuthorizedClientManager clientManager;
    private final CircuitBreakerRegistry circuitBreakerRegistry;


    @CircuitBreaker(name = "front-circuitbreaker", fallbackMethod = "fallbackRegistration")
    public ResponseAccountDto registration(CreateAccountDto dto) {

        return client.post()
                .uri("http://gateway/account/registration")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .bodyValue(dto).retrieve()
                .onStatus(status -> status == HttpStatus.CONFLICT,
                        response -> response.bodyToMono(ApiError.class)
                                .flatMap(body -> Mono.error(new AccountException(dto,
                                        body.getMessage()))))

                .bodyToMono(ResponseAccountDto.class).
                block();
    }

    @CircuitBreaker(name = "front-circuitbreaker", fallbackMethod = "fallbackGetAccount")
    public ResponseAccountInfoDto getAccount(String username) {
        return client.get()
                .uri("http://gateway/account/" + username)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .retrieve()
                .onStatus(status -> status == HttpStatus.CONFLICT,
                        response -> Mono.error(new UsernameNotFoundException(username)))
                .onStatus(status -> status == HttpStatus.FORBIDDEN,
                        response -> Mono.error(new LoginException("403 Forbidden from GET account service", HttpStatus.FORBIDDEN.toString())))

                .bodyToMono(ResponseAccountInfoDto.class).
                block();
    }

    private String getAccessToken() {
        OAuth2AuthorizedClient system = clientManager.authorize(OAuth2AuthorizeRequest
                .withClientRegistrationId("account-client")
                .principal("system").build());
        return system.getAccessToken().getTokenValue();
    }

    private ResponseAccountDto fallbackRegistration(Exception ex) {
        log.info("execute fallbackRegistration: {}", ex.getMessage());
        return new ResponseAccountDto(-1L, Set.of(), "error", "error", "error", LocalDate.now());
    }

    private ResponseAccountInfoDto fallbackGetAccount(Throwable ex) {
        log.info("execute fallbackGetAccount: {}", ex.getMessage());
        return null;
    }
}
