package org.ivanov.transferdto.innertransferdto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class InnerTransferReqDto {
    private Long sourceWalletId;
    private Long targetWalletId;
    private BigDecimal amount;
    private String email;
}
