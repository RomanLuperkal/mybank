package org.ivanov.transfer.client.impl;

import org.blog.blockdto.block.ResponseValidatedTransactionDto;
import org.blog.blockdto.block.UnvalidatedTransactionDto;

import org.ivanov.transfer.client.BlockClient;
import org.ivanov.transfer.client.KeycloakManageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class BlockClientImpl implements BlockClient {

    @Qualifier("service-client")
    @Autowired
    private WebClient client;
    @Autowired
    private KeycloakManageClient keycloakManageClient;
    @Value("${block-service.host}")
    private String blockServiceHost;

    @Override
    public ResponseValidatedTransactionDto validateTransaction(UnvalidatedTransactionDto dto) {
        return client.post()
                .uri(blockServiceHost + "/block")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + keycloakManageClient.getAccessToken())
                .bodyValue(dto).retrieve()
                .bodyToMono(ResponseValidatedTransactionDto.class)
                //.retry(3)
                .block();
    }
}
