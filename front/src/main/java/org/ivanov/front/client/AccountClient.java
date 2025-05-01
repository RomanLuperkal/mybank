package org.ivanov.front.client;

import lombok.RequiredArgsConstructor;
import org.ivanov.accountdto.account.CreateAccountDto;
import org.ivanov.accountdto.account.ResponseAccountDto;
import org.ivanov.accountdto.account.ResponseAccountInfoDto;
import org.ivanov.front.handler.exception.AccountException;
import org.ivanov.front.handler.response.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AccountClient {
    private final WebClient client;

    public ResponseAccountDto registration(CreateAccountDto dto) {
        return client.post()
                .uri("/account/registration")
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
                .retrieve()
                .onStatus(status -> status == HttpStatus.CONFLICT,
                        response -> Mono.error(new UsernameNotFoundException(username)))

                .bodyToMono(ResponseAccountInfoDto.class).
                block();
    }
}
