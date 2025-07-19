package org.ivanov.front.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ivanov.exchangedto.currency.ResponseCurrencyDto;
import org.ivanov.front.configuration.security.AccountUserDetails;
import org.ivanov.front.service.ExchangeService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/exchange")
@Slf4j
public class ExchangeController {
    private final ExchangeService exchangeService;

    @GetMapping
    public String getExchange(Model model, Authentication authentication) {
        log.info("Получен запрос get /exchange");
        AccountUserDetails accountUserDetails = getAccountUserDetails(authentication);
        ResponseCurrencyDto exchange = exchangeService.getExchange();
        log.debug("accountUserDetails: {}", accountUserDetails);
        log.debug("exchange: {}", exchange);

        model.addAttribute("exchange", exchange);
        model.addAttribute("user", accountUserDetails);
        model.addAttribute("currency", exchange);
        return "exchange";
    }

    private AccountUserDetails getAccountUserDetails(Authentication authentication) {
        return (AccountUserDetails) authentication.getPrincipal();
    }
}
