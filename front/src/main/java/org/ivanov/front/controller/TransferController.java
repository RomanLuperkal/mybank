package org.ivanov.front.controller;

import lombok.RequiredArgsConstructor;
import org.ivanov.accountdto.wallet.ResponseWalletDto;
import org.ivanov.front.configuration.security.AccountUserDetails;
import org.ivanov.front.service.AccountService;
import org.ivanov.front.service.TransferService;
import org.ivanov.transferdto.ExternalTransferDto;
import org.ivanov.transferdto.ResponseTransferDto;
import org.ivanov.transferdto.TransferReqDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping("/transfer")
@RequiredArgsConstructor
public class TransferController {
    private final TransferService transferService;
    private final AccountService accountService;

    @PostMapping("/inner")
    public ResponseEntity<ResponseTransferDto> createInnerTransfer(@RequestBody TransferReqDto dto, Authentication authentication) {
        return ResponseEntity.status(202).body(transferService.createInnerTransfer(dto, getUserWallets(authentication)));
    }

    @GetMapping("/inner")
    public String getInnerTransfer(Model model, Authentication authentication) {
        model.addAttribute("user", accountService.getAccountInfo(getUsername(authentication)));
        return "inner-transfer";
    }

    private String getUsername(Authentication authentication) {
        AccountUserDetails principal = (AccountUserDetails) authentication.getPrincipal();
        return principal.getUsername();
    }

    private Set<ResponseWalletDto> getUserWallets(Authentication authentication) {
        return  accountService.getAccountInfo(getUsername(authentication)).wallets();
    }

    public ResponseEntity<ResponseTransferDto> createExternalTransfer(@RequestBody ExternalTransferDto dto, Authentication authentication) {

    }
}
