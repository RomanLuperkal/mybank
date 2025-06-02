package org.blog.cashservice.controller;

import lombok.RequiredArgsConstructor;
import org.blog.cashdto.cash.ResponseCashDto;
import org.blog.cashdto.cash.UpdateCashDto;
import org.blog.cashservice.service.CashService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cash")
@RequiredArgsConstructor
public class CashController {
    private final CashService cashService;

    @PatchMapping()
    public ResponseEntity<ResponseCashDto> updateWallet(@RequestBody UpdateCashDto dto) {
        return ResponseEntity.ok(cashService.createTransaction(dto));
    }
}
