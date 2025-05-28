package org.ivanov.front.controller;

import lombok.RequiredArgsConstructor;
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
public class ExchangeController {
    private final ExchangeService exchangeService;

    @GetMapping
    public String getExchange(Model model, Authentication authentication) {
        model.addAttribute("user", getAccountUserDetails(authentication));
        model.addAttribute("currency", exchangeService.getExchange());
        return "exchange";
    }

    private AccountUserDetails getAccountUserDetails(Authentication authentication) {
        return (AccountUserDetails) authentication.getPrincipal();
    }
}
