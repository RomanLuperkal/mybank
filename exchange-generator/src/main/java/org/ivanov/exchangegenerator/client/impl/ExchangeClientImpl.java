package org.ivanov.exchangegenerator.client.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.ivanov.exchangedto.currency.CreateCurrencyDto;
import org.ivanov.exchangegenerator.client.ExchangeClient;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;

@Component
@RequiredArgsConstructor
public class ExchangeClientImpl implements ExchangeClient {

    private final OAuth2AuthorizedClientManager clientManager;
    private final WebClient client;
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final ObjectMapper mapper;

    @Override
    public void changeExchange(CreateCurrencyDto dto) {
        client.patch()
                .uri("http://gateway/exchange//change")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .bodyValue(dto).retrieve()
                .bodyToMono(Void.class)
                //.retry(3)
                .block();
    }

    public String getAccessToken() {
        //TODO привести в человеческий вид а так же прописать все роли в клиентах в realm.json
        String tokenUrl = "http://localhost:9092/realms/master/protocol/openid-connect/token";
        String clientId = "exchange-client";
        String clientSecret = "FPMcaxvSBkPz7nLllGSCKvSEvDy4X2Rs";

        String credentials = Base64.getEncoder().encodeToString(
                (clientId + ":" + clientSecret).getBytes()
        );

        return WebClient.builder()
                .baseUrl(tokenUrl)
                .build()
                .post()
                .header("Authorization", "Basic " + credentials)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue("grant_type=client_credentials")
                .retrieve()
                .bodyToMono(String.class)
                .<String>handle((json, sink) -> {
                    try {

                        JsonNode node = mapper.readTree(json);
                        sink.next(node.get("access_token").asText());
                    } catch (Exception e) {
                        sink.error(new RuntimeException("Ошибка парсинга JSON", e));
                    }
                })
                .block();
    }
}

