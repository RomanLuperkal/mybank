package org.ivanov.front.controller;

import lombok.RequiredArgsConstructor;
import org.blog.cashdto.cash.ResponseCashDto;
import org.blog.cashdto.cash.UpdateCashDto;
import org.ivanov.front.configuration.security.AccountUserDetails;
import org.ivanov.front.service.CashService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cash")
@RequiredArgsConstructor
public class CashController {
    private final CashService cashService;

    @GetMapping
    public String cash(Authentication authentication, Model model) {
        AccountUserDetails accountUserDetails = getAccountUserDetails(authentication);
        model.addAttribute("user", accountUserDetails);
        return "cash";
    }

    @PatchMapping
    @ResponseBody
    public ResponseEntity<ResponseCashDto> updateCash(UpdateCashDto dto) {
        return ResponseEntity.ok(cashService.updateCash(dto));
    }

    private AccountUserDetails getAccountUserDetails(Authentication authentication) {
        return (AccountUserDetails) authentication.getPrincipal();
    }
}
