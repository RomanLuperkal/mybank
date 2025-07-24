package org.ivanov.account.client.impl;

import org.blog.notificationdto.notificationoutbox.CreateMessageDto;
import org.ivanov.account.client.KeycloakManageClient;
import org.ivanov.account.client.NotificationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class NotificationClientImpl implements NotificationClient {

    @Qualifier("notification-client")
    @Autowired
    private WebClient notificationClient;
    @Autowired
    private KeycloakManageClient keycloakManageClient;

    @Value("${notification-service.host}")
    private String notificationHost;

    public void sentMessage(List<CreateMessageDto> dto) {

         notificationClient.post()
                .uri(notificationHost + "/notification")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + keycloakManageClient.getAccessToken())
                .bodyValue(dto).retrieve()
                .bodyToMono(Void.class)
                //.retry(3)
                .block();
    }
}
