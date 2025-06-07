package org.ivanov.front.controller;

import lombok.RequiredArgsConstructor;
import org.ivanov.front.service.TransferService;
import org.ivanov.transferdto.ResponseTransferDto;
import org.ivanov.transferdto.innertransferdto.InnerTransferReqDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/transfer")
@RequiredArgsConstructor
public class TransferController {
    private final TransferService transferService;

    @PostMapping("/inner")
    public ResponseEntity<ResponseTransferDto> createInnerTransfer(@RequestBody InnerTransferReqDto dto) {
        return ResponseEntity.ok(transferService.createInnerTransfer(dto));
    }
}
