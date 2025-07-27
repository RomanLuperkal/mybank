package org.blog.cashservice.client.impl;

import org.blog.cashservice.client.NotificationClient;
import org.blog.cashservice.client.KeycloakManageClient;
import org.blog.notificationdto.notificationoutbox.CreateMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${notification-service.host}")
    private String notificationServiceHost;

    public void sentMessage(List<CreateMessageDto> dto) {

         notificationClient.post()
                .uri(notificationServiceHost + "/notification")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + keycloakManageClient.getAccessToken())
                .bodyValue(dto).retrieve()
                .bodyToMono(Void.class)
                //.retry(3)
                .block();
    }
}
