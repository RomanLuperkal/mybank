package org.ivanov.transfer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;
    @Column(name = "source_wallet_id")
    private Long sourceWalletId;
    @Column(name = "username_target_account")
    private String usernameTargetAccount;
    @Column(name = "target_wallet_type")
    private String targetWalletType;
    private BigDecimal amount;
    private String email;
    @Enumerated(EnumType.STRING)
    private Status status = Status.WAITING;

    public enum Status {
        WAITING, APPROVED, DONE, ERROR, BLOCKED
    }
}
