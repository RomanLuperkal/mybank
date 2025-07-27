package org.ivanov.transfer.client.impl;

import org.ivanov.accountdto.wallet.ResponseExchangeWalletsDto;
import org.ivanov.transfer.client.AccountClient;
import org.ivanov.transfer.client.KeycloakManageClient;
import org.ivanov.transferdto.ReqExchangeWalletsDto;
import org.ivanov.transferdto.ReqTransferMoneyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;

@Component
public class AccountClientImpl implements AccountClient {
    @Qualifier("service-client")
    @Autowired
    private WebClient client;
    @Autowired
    private KeycloakManageClient keycloakManageClient;
    @Value("${account-service.host}")
    private String accountServiceHost;

    @Override
    public CompletableFuture<ResponseExchangeWalletsDto> getWalletsType(ReqExchangeWalletsDto dto) {
        return client.post()
                .uri(accountServiceHost + "/account/wallet/types")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + keycloakManageClient.getAccessToken())
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(ResponseExchangeWalletsDto.class)
                //.retry(3)
                .toFuture();
    }

    @Override
    public void processTransferTransaction(ReqTransferMoneyDto dto) {
         client.patch()
                .uri(accountServiceHost + "/account/wallet/transfer")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + keycloakManageClient.getAccessToken())
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(Void.class)
                //.retry(3)
                .toFuture();
    }
}
