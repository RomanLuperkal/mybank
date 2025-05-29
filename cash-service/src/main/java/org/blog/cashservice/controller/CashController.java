package org.blog.cashservice.controller;

import lombok.RequiredArgsConstructor;
import org.blog.cashdto.cash.ResponseCashDto;
import org.blog.cashdto.cash.UpdateCashDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cash")
@RequiredArgsConstructor
public class CashController {

    @PatchMapping("/create")
    public ResponseEntity<ResponseCashDto> updateWallet(@RequestBody UpdateCashDto dto) {
        return null;
    }
}
