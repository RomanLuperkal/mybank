package org.ivanov.front.client;

import lombok.RequiredArgsConstructor;
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

@Component
@RequiredArgsConstructor
public class AccountClient {
    private final WebClient client;
    private final OAuth2AuthorizedClientManager clientManager;

    public ResponseAccountDto registration(CreateAccountDto dto) {
        return client.post()
                .uri("/account/registration")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .bodyValue(dto).retrieve()
                .onStatus(status -> status == HttpStatus.CONFLICT,
                        response -> response.bodyToMono(ApiError.class)
                                .flatMap(body -> Mono.error(new AccountException(dto,
                                        body.getMessage()))))

                .bodyToMono(ResponseAccountDto.class).
                block();
    }

    public ResponseAccountInfoDto getAccount(String username) {
        return client.get()
                .uri("/account/" + username)
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
}
