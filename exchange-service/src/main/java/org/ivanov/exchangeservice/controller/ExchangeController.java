package org.ivanov.exchangeservice.controller;

import lombok.RequiredArgsConstructor;
import org.ivanov.exchangedto.currency.CreateCurrencyDto;
import org.ivanov.exchangedto.currency.ResponseCurrencyDto;
import org.ivanov.exchangeservice.service.ExchangeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exchange")
@RequiredArgsConstructor
public class ExchangeController {
    private final ExchangeService exchangeService;

    @PatchMapping("/change")
    public void changeExchange(@RequestBody CreateCurrencyDto dto) {
        exchangeService.changeExchange(dto);
    }

    @GetMapping
    public ResponseEntity<ResponseCurrencyDto> getExchange() {
        return ResponseEntity.ok(exchangeService.getExchange());
    }
}
