package org.ivanov.transfer.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ivanov.transfer.service.TransferService;
import org.ivanov.transferdto.ResponseTransferDto;
import org.ivanov.transferdto.TransferReqDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfer")
@RequiredArgsConstructor
@Slf4j
public class TransferController {
    private final TransferService transferService;

    @PostMapping("/inner")
    public ResponseEntity<ResponseTransferDto> createInnerTransfer(@RequestBody TransferReqDto dto) {
        log.info("поступил запрос post /inner");
        log.debug("TransferReqDto: {}", dto);
        return ResponseEntity.ok(transferService.createInnerTransfer(dto));
    }
}
