package org.ivanov.front.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blog.cashdto.cash.ResponseCashDto;
import org.blog.cashdto.cash.UpdateCashDto;
import org.ivanov.accountdto.account.ResponseAccountDto;
import org.ivanov.front.configuration.security.AccountUserDetails;
import org.ivanov.front.service.AccountService;
import org.ivanov.front.service.CashService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cash")
@RequiredArgsConstructor
@Slf4j
public class CashController {
    private final CashService cashService;
    private final AccountService accountService;

    @GetMapping
    public String cash(Authentication authentication, Model model) {
        log.info("Получен запрос get /cash");
        model.addAttribute("user", accountService.getAccountInfo(getUsername(authentication)));
        return "cash";
    }

    @PatchMapping
    @ResponseBody
    public ResponseEntity<ResponseCashDto> updateCash(@RequestBody UpdateCashDto dto, Authentication authentication) {
        log.info("Получен запрос patch /cash");
        ResponseAccountDto accountInfo = accountService.getAccountInfo(getUsername(authentication));
        return ResponseEntity.ok(cashService.updateCash(dto, accountInfo));
    }

    private String getUsername(Authentication authentication) {
        AccountUserDetails principal = (AccountUserDetails) authentication.getPrincipal();
        return principal.getUsername();
    }
}
