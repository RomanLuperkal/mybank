package org.blog.cashservice.client.impl;

import org.blog.cashservice.client.NotificationClient;
import org.blog.cashservice.configuration.KeycloakManageClient;
import org.blog.notificationdto.notificationoutbox.CreateMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class NotificationClientImpl implements NotificationClient {

    @Qualifier("service-client")
    @Autowired
    private WebClient notificationClient;
    @Autowired
    private KeycloakManageClient keycloakManageClient;

    public void sentMessage(List<CreateMessageDto> dto) {

         notificationClient.post()
                .uri("http://gateway/notification")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + keycloakManageClient.getAccessToken())
                .bodyValue(dto).retrieve()
                .bodyToMono(Void.class)
                //.retry(3)
                .block();
    }
}
