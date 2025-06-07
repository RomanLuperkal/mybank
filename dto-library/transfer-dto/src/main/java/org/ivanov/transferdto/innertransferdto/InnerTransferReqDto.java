package org.ivanov.transferdto.innertransferdto;

import java.math.BigDecimal;

public record InnerTransferReqDto(Long sourceWalletId, String targetWalletType, BigDecimal amount, String email) {
}
