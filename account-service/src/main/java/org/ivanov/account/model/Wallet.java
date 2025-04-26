package org.ivanov.account.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wallet_id")
    private Long walletId;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    @Column(name = "wallet_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private WalletType walletType;
    private BigDecimal balance = BigDecimal.ZERO;

    public enum WalletType {
        RUB, USD, CNY
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Wallet wallet = (Wallet) o;
        return getWalletId() != null && Objects.equals(getWalletId(), wallet.getWalletId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
