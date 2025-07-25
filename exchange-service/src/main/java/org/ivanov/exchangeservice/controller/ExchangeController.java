package org.ivanov.exchangeservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ivanov.exchangedto.currency.CreateCurrencyDto;
import org.ivanov.exchangedto.currency.ResponseCurrencyDto;
import org.ivanov.exchangeservice.service.ExchangeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exchange")
@RequiredArgsConstructor
@Slf4j
public class ExchangeController {
    private final ExchangeService exchangeService;

    @PatchMapping("/change")
    public void changeExchange(@RequestBody CreateCurrencyDto dto) {
        log.info("Поступил запрос patch /change");
        log.info("dto: {}", dto);
        exchangeService.changeExchange(dto);
    }

    @GetMapping
    public ResponseEntity<ResponseCurrencyDto> getExchange() {
        log.info("Поступил запрос get exchange");
        ResponseCurrencyDto exchange = exchangeService.getExchange();
        log.info("ResponseCurrencyDto: {}", exchange);
        return ResponseEntity.ok(exchange);
    }
}
