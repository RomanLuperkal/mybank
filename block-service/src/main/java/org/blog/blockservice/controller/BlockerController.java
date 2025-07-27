package org.blog.blockservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class BlockerController {
    private final BlockerService blockerService;

    @PostMapping
    public ResponseEntity<ResponseValidatedTransactionDto> validateTransaction(@RequestBody UnvalidatedTransactionDto dto) {
        log.info("Поступил запрос post /block");
        ResponseValidatedTransactionDto result = blockerService.validateTransaction(dto);
        log.debug("result: {}", result);
        return ResponseEntity.ok(result);
    }
}
