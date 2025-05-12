package org.ivanov.front.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ivanov.accountdto.account.CreateAccountDto;
import org.ivanov.accountdto.account.ResponseAccountDto;
import org.ivanov.accountdto.account.ResponseAccountInfoDto;
import org.ivanov.accountdto.account.UpdatePasswordDto;
import org.ivanov.front.handler.exception.AccountException;
import org.ivanov.front.handler.exception.RegistrationException;
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

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccountClientImpl implements AccountClient {
    private final WebClient client;
    private final OAuth2AuthorizedClientManager clientManager;


    @CircuitBreaker(name = "front-circuitbreaker", fallbackMethod = "fallbackRegistration")
    public ResponseAccountDto registration(CreateAccountDto dto) {

        return client.post()
                .uri("http://gateway/account/registration")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .bodyValue(dto).retrieve()
                .onStatus(status -> status == HttpStatus.CONFLICT || status == HttpStatus.FORBIDDEN || status == HttpStatus.GATEWAY_TIMEOUT,
                        response -> response.bodyToMono(ApiError.class)
                                .flatMap(body -> Mono.error(new RegistrationException(dto,
                                        body.getMessage()))))
                .bodyToMono(ResponseAccountDto.class)
                .retry(3)
                .block();
    }

    @CircuitBreaker(name = "front-circuitbreaker", fallbackMethod = "fallbackGetAccount")
    public ResponseAccountDto getAccount(String username) {
        return client.get()
                .uri("http://gateway/account/" + username)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .retrieve()
                .onStatus(status -> status == HttpStatus.CONFLICT,
                        response -> Mono.error(new UsernameNotFoundException(username)))
                .onStatus(status -> status == HttpStatus.FORBIDDEN,
                        response -> Mono.error(new AccountException("403 Forbidden from GET account service", HttpStatus.FORBIDDEN.toString())))
                .onStatus(status -> status == HttpStatus.GATEWAY_TIMEOUT,
                        response -> response.bodyToMono(ApiError.class)
                                .flatMap(body -> Mono.error(new AccountException(body.getMessage(), HttpStatus.GATEWAY_TIMEOUT.toString()))))
                .bodyToMono(ResponseAccountDto.class)
                .retry(3)
                . block();
    }

    @Override
    public void deleteAccount(Long accountId) {
        client.delete()
                .uri("http://gateway/account/" + accountId + "/delete-account")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .retrieve()
                .onStatus(status -> status == HttpStatus.CONFLICT,
                        response -> response.bodyToMono(ApiError.class)
                                .flatMap(body -> Mono.error(new AccountException(body.getMessage(), HttpStatus.CONFLICT.toString()))))
                .onStatus(status -> status == HttpStatus.FORBIDDEN,
                        response -> Mono.error(new AccountException("403 Forbidden from GET account service", HttpStatus.FORBIDDEN.toString())))
                .onStatus(status -> status == HttpStatus.GATEWAY_TIMEOUT,
                        response -> response.bodyToMono(ApiError.class)
                                .flatMap(body -> Mono.error(new AccountException(body.getMessage(), HttpStatus.GATEWAY_TIMEOUT.toString()))))
                .bodyToMono(Void.class)
                .retry(3)
                . block();
    }

    @Override
    public void changePassword(Long accountId, UpdatePasswordDto password) {
        client.patch()
                .uri("http://gateway/account/" + accountId + "/change-password")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .bodyValue(password)
                .retrieve()
                .onStatus(status -> status == HttpStatus.CONFLICT,
                        response -> response.bodyToMono(ApiError.class)
                                .flatMap(body -> Mono.error(new AccountException(body.getMessage(), HttpStatus.CONFLICT.toString()))))
                .onStatus(status -> status == HttpStatus.FORBIDDEN,
                        response -> Mono.error(new AccountException("403 Forbidden from GET account service", HttpStatus.FORBIDDEN.toString())))
                .onStatus(status -> status == HttpStatus.GATEWAY_TIMEOUT,
                        response -> response.bodyToMono(ApiError.class)
                                .flatMap(body -> Mono.error(new AccountException(body.getMessage(), HttpStatus.GATEWAY_TIMEOUT.toString()))))
                .bodyToMono(Void.class)
                .retry(3)
                . block();
    }

    private String getAccessToken() {
        OAuth2AuthorizedClient system = clientManager.authorize(OAuth2AuthorizeRequest
                .withClientRegistrationId("account-client")
                .principal("system").build());
        return system.getAccessToken().getTokenValue();
    }

    private ResponseAccountDto fallbackRegistration(RuntimeException e) {
        final String message  = "Аккаунт сервис недоступен";
        var stubDto = new CreateAccountDto(null, null, null, null, null, null);
        log.info("execute fallbackRegistration: {}", e.getMessage());
        handleCircuitBreakerFailure(e, List.of(AccountException.class, RegistrationException.class), ResponseAccountDto.class);
        throw new RegistrationException(stubDto, message);
    }

    private ResponseAccountDto fallbackGetAccount(RuntimeException e) {
        final String message  = "Аккаунт сервис недоступен";
        log.info("execute fallbackGetAccount: {}", e.getMessage());
        handleCircuitBreakerFailure(e, List.of(AccountException.class, UsernameNotFoundException.class),
                 ResponseAccountInfoDto.class);
        throw new AccountException(message, HttpStatus.INTERNAL_SERVER_ERROR.toString());
    }

    private <T> void handleCircuitBreakerFailure(RuntimeException e, List<Class<? extends RuntimeException>> exceptions, Class<T> clazz) {
        for (Class<? extends RuntimeException> exceptionClass : exceptions) {
            if (exceptionClass.isInstance(e)) {
                throw e;
            }
        }
    }
}
