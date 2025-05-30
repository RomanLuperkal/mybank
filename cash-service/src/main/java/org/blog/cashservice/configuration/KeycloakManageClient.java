package org.blog.cashservice.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;

@Component
public class KeycloakManageClient {
    @Qualifier("keycloak-client")
    @Autowired
    private WebClient keycloakClient;
    @Autowired
    private ObjectMapper mapper;
    private String accessToken;
    private long tokenExpiryTime;
    //@Value("${spring.security.oauth2.client.registration.notification-client.client-id}")
    private String clientId;
    //@Value("${spring.security.oauth2.client.registration.notification-client.client-secret}")
    private String clientSecret;

    public String getAccessToken() {
        //TODO привести в человеческий вид а так же прописать все роли в клиентах в realm.json
        long currentTime = System.currentTimeMillis();

        if (accessToken != null && tokenExpiryTime > currentTime) {
            return accessToken;
        }

        return fetchNewToken();
    }

    private String fetchNewToken() {

        String credentials = Base64.getEncoder().encodeToString(
                (clientId + ":" + clientSecret).getBytes()
        );

        return keycloakClient
                .post()
                .header("Authorization", "Basic " + credentials)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue("grant_type=client_credentials")
                .retrieve()
                .bodyToMono(String.class)
                .<String>handle((json, sink) -> {
                    try {
                        JsonNode node = mapper.readTree(json);
                        String accessToken1 = node.get("access_token").asText();
                        int expiresIn = node.get("expires_in").asInt(); // В секундах
                        this.accessToken = accessToken1;
                        this.tokenExpiryTime = System.currentTimeMillis() + (expiresIn * 1000L);
                        sink.next(accessToken1);
                    } catch (Exception e) {
                        sink.error(new RuntimeException("Ошибка парсинга JSON", e));
                    }
                })
                .block();
    }
}
