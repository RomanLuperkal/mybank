package org.blog.blockservice.controller;

import lombok.RequiredArgsConstructor;
import org.blog.blockdto.block.ResponseValidatedTransactionDto;
import org.blog.blockdto.block.UnvalidatedTransactionDto;
import org.blog.blockservice.service.BlockerService;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/block")
@RequiredArgsConstructor
public class BlockerController {
    private final BlockerService blockerService;

    @PostMapping
    public ResponseEntity<ResponseValidatedTransactionDto> validateTransaction(@RequestBody UnvalidatedTransactionDto dto) {
        return ResponseEntity.ok(blockerService.validateTransaction(dto));
    }
}
