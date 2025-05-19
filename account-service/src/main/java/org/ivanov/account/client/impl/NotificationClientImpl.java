package org.ivanov.account.client.impl;

import lombok.RequiredArgsConstructor;
import org.blog.notificationdto.notificationoutbox.CreateMessageDto;
import org.ivanov.account.client.NotificationClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class NotificationClientImpl implements NotificationClient {
    private final WebClient client;

    public void sentMessage(CreateMessageDto dto) {

         client.post()
                .uri("http://gateway/notification/")
                //.header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .bodyValue(dto).retrieve()
                .bodyToMono(Void.class)
                //.retry(3)
                .block();
    }
}
