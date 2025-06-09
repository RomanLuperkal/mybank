package org.ivanov.accountdto.wallet;


public record ResponseExchangeWalletsDto(WalletType sourceWallet, WalletType targetWallet) {
    public enum WalletType {
        RUB, USD, CNY
    }
}
