package org.ivanov.gateway.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Collections;
import java.util.List;

@Component
public class TokenRelayToKeycloak extends AbstractGatewayFilterFactory<TokenRelayToKeycloak.Config> {

    private final ReactiveOAuth2AuthorizedClientManager clientManager;

    public TokenRelayToKeycloak(ReactiveOAuth2AuthorizedClientManager clientManager) {
        super(Config.class);
        this.clientManager = clientManager;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String clientRegistrationId = config.getClient();

            // Получаем токен от Keycloak
            return clientManager.authorize(
                            OAuth2AuthorizeRequest.withClientRegistrationId(clientRegistrationId).principal("system").build()
                    )
                    .flatMap(client -> {
                        String accessToken = client.getAccessToken().getTokenValue();

                        // Добавляем токен в заголовок Authorization: Bearer <token>
                        ServerWebExchange modifiedExchange = exchange.mutate()
                                .request(builder -> builder.header("Authorization", "Bearer " + accessToken))
                                .build();

                        return chain.filter(modifiedExchange);
                    });
        };
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("client");
    }


    @Getter
    @Setter
    public static class Config {
        private String client;

        public void setClient(String client) {
            this.client = client;
        }
    }
}
