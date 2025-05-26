package org.ivanov.account.client.impl;

import lombok.RequiredArgsConstructor;
import org.blog.notificationdto.notificationoutbox.CreateMessageDto;
import org.ivanov.account.client.NotificationClient;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationClientImpl implements NotificationClient {
    private final WebClient client;
    private final OAuth2AuthorizedClientManager clientManager;

    public void sentMessage(List<CreateMessageDto> dto) {

         client.post()
                .uri("http://gateway/notification")
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
