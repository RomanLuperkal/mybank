package org.ivanov.transfer.client.impl;

import org.ivanov.accountdto.wallet.ResponseWalletDto;
import org.ivanov.transfer.client.AccountClient;
import org.ivanov.transfer.client.KeycloakManageClient;
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

    //TODO переделать на CompletableFeature
    @Override
    public ResponseWalletDto getWallet(Long walletId) {
        return client.get()
                .uri("http://gateway/account/wallet/" + walletId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + keycloakManageClient.getAccessToken())
                .retrieve()
                .bodyToMono(ResponseWalletDto.class)
                //.retry(3)
                .block();
    }
}
