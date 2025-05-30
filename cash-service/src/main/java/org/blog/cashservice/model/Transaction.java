package org.blog.cashservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "cash_transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    @Column(name = "account_id")
    private Long accountId;
    @Column(name = "wallet_id")
    private Long walletId;
    private BigDecimal amount;
    @Column(name = "transaction_type")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Enumerated(EnumType.STRING)
    private Status status = Status.WAITING;
    private String email;


    public enum TransactionType {
        ADD, REMOVE
    }

    public enum Status {
        WAITING, APPROVED, DONE, ERROR, BLOCKED
    }
}
