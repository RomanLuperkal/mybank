package org.blog.cashservice.client.impl;

import org.blog.blockdto.block.ResponseValidatedTransactionDto;
import org.blog.blockdto.block.UnvalidatedTransactionDto;
import org.blog.cashservice.client.BlockClient;
import org.blog.cashservice.client.KeycloakManageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class BlockClientImpl implements BlockClient {

    @Qualifier("service-client")
    @Autowired
    private WebClient client;
    @Autowired
    private KeycloakManageClient keycloakManageClient;

    @Override
    public ResponseValidatedTransactionDto validateTransaction(UnvalidatedTransactionDto dto) {
        return client.post()
                .uri("http://gateway/block")
                //.header(HttpHeaders.AUTHORIZATION, "Bearer " + keycloakManageClient.getAccessToken())
                .bodyValue(dto).retrieve()
                .bodyToMono(ResponseValidatedTransactionDto.class)
                //.retry(3)
                .block();
    }
}
