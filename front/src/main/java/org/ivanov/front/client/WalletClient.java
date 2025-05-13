package org.ivanov.front.client;

import org.ivanov.accountdto.wallet.CreateWalletDto;
import org.ivanov.accountdto.wallet.ResponseWalletDto;

public interface WalletClient {
    ResponseWalletDto createWallet(CreateWalletDto createWalletDto);
}
