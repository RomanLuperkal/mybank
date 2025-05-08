package org.ivanov.gateway.controller;

import org.ivanov.gateway.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/fallback")
public class AccountFallbackController {

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseStatus(HttpStatus.GATEWAY_TIMEOUT)
    public Mono<ApiError> fallback() {
        return Mono.just(new ApiError("Service is unavailable.", HttpStatus.SERVICE_UNAVAILABLE.toString()));
    }
}
