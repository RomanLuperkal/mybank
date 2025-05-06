package org.ivanov.front.configuration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {
    @Bean
    @LoadBalanced
    public WebClient webClient(ReactorLoadBalancerExchangeFilterFunction lbFunction) {
        return WebClient.builder().filter(lbFunction).build();
    }
}
