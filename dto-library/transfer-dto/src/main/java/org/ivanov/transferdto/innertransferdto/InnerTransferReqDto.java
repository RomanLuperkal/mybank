package org.ivanov.transferdto.innertransferdto;

import java.math.BigDecimal;

public record InnerTransferReqDto(Long sourceWalletId, Long targetWalletId, BigDecimal amount, String email) {
}
