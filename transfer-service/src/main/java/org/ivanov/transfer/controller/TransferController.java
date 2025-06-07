package org.ivanov.transfer.controller;

import lombok.RequiredArgsConstructor;
import org.ivanov.transferdto.ResponseTransferDto;
import org.ivanov.transferdto.innertransferdto.InnerTransferReqDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfer")
@RequiredArgsConstructor
public class TransferController {

    @PostMapping("/inner")
    public ResponseEntity<ResponseTransferDto> createInnerTransfer(@RequestBody InnerTransferReqDto dto) {
        return null;
    }
}
