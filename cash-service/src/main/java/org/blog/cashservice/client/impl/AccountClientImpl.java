package org.blog.cashservice.client.impl;

import org.blog.cashdto.transaction.ApprovedTransactionDto;
import org.blog.cashservice.client.AccountClient;
import org.blog.cashservice.client.KeycloakManageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AccountClientImpl implements AccountClient {
    @Qualifier("service-client")
    @Autowired
    private WebClient client;
    @Autowired
    private KeycloakManageClient keycloakManageClient;

    @Override
            //TODO передать в эндпоинт accountId и walletId
    public void processTransaction(Long accountId, Long walletId, ApprovedTransactionDto dto) {
         client.patch()
                .uri("http://gateway/account/" + accountId + "/wallet/" + walletId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + keycloakManageClient.getAccessToken())
                .bodyValue(dto).retrieve()
                .bodyToMono(Void.class)
                //.retry(3)
                .block();
    }
}
