package org.ivanov.transferdto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransferReqDto {
    private Long sourceWalletId;
    private Long targetWalletId;
    private BigDecimal amount;
    private String email;
}
