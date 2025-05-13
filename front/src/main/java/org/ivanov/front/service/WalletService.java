package org.ivanov.front.service;

import org.ivanov.accountdto.wallet.CreateWalletDto;
import org.ivanov.accountdto.wallet.ResponseWalletDto;

public interface WalletService {
    ResponseWalletDto createWallet(CreateWalletDto createWalletDto);
}
