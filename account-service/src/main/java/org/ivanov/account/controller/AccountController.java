package org.ivanov.account.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blog.cashdto.transaction.ApprovedTransactionDto;
import org.ivanov.account.service.AccountService;
import org.ivanov.account.service.WalletService;
import org.ivanov.accountdto.account.CreateAccountDto;
import org.ivanov.accountdto.account.ResponseAccountDto;
import org.ivanov.accountdto.account.UpdatePasswordDto;
import org.ivanov.accountdto.account.UpdateProfileDto;
import org.ivanov.accountdto.wallet.CreateWalletDto;
import org.ivanov.accountdto.wallet.ReqWalletInfoDto;
import org.ivanov.accountdto.wallet.ResponseExchangeWalletsDto;
import org.ivanov.accountdto.wallet.ResponseWalletDto;
import org.ivanov.transferdto.ReqExchangeWalletsDto;
import org.ivanov.transferdto.ReqTransferMoneyDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Slf4j
public class AccountController {
    private final AccountService accountService;
    private final WalletService walletService;

    @PostMapping("/registration")
    public ResponseEntity<ResponseAccountDto> registry(@Valid @RequestBody CreateAccountDto createAccountDto) {
        log.info("Получен запрос post /registration");
        log.debug("createAccountDto: {}", createAccountDto);

        ResponseAccountDto account = accountService.createAccount(createAccountDto);
        log.debug("account: {}", account);

        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    @GetMapping("{username}")
    public ResponseEntity<ResponseAccountDto> getAccountInfo(@PathVariable String username) {
        log.info("Получен запрос get /account/{}", username);

        ResponseAccountDto accountInfo = accountService.getAccountInfo(username);
        log.debug("accountInfo: {}", accountInfo);

        return ResponseEntity.status(HttpStatus.OK).body(accountInfo);
    }

    @DeleteMapping("{accountId}/delete-account")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable long accountId) {
        log.info("Получен запрос delete {}/delete-account", accountId);
        accountService.deleteAccount(accountId);
    }

    @PatchMapping("/{accountId}/change-password")
    @ResponseStatus(HttpStatus.OK)
    public void updatePassword(@PathVariable Long accountId,
                               @RequestBody UpdatePasswordDto newPassword) {
        log.info("Получен запрос patch /{}/change-password", accountId);
        log.debug("newPassword: {}", newPassword);

        accountService.updatePassword(accountId, newPassword);
    }

    @PatchMapping("/{accountId}/update-profile")
    @ResponseStatus(HttpStatus.OK)
    public void updateProfile(@PathVariable Long accountId, @RequestBody UpdateProfileDto dto) {
        log.info("Получен запрос patch /{}/update-profile", accountId);
        log.debug("UpdateProfileDto: {}", dto);
        accountService.updateProfile(accountId, dto);
    }

    @PostMapping("/{accountId}/wallet")
    public ResponseEntity<ResponseWalletDto> createWallet(@PathVariable Long accountId,
                                                          @Valid @RequestBody CreateWalletDto createWalletDto) {
        log.info("Получен запрос post /{}/wallet", accountId);
        log.debug("createWalletDto: {}", createWalletDto);
        return ResponseEntity.ok(walletService.createWallet(accountId, createWalletDto));
    }

    @DeleteMapping("/{accountId}/wallet/{walletId}")
    public ResponseEntity<ResponseWalletDto> deleteWallet(@PathVariable Long accountId, @PathVariable Long walletId) {
        log.info("Получен запрос delete /{}/wallet/{}", accountId, walletId);
        return ResponseEntity.ok(walletService.deleteWallet(accountId, walletId));
    }

    @PatchMapping("/{accountId}/wallet/{walletId}")
    public ResponseEntity<Void> processTransaction(@PathVariable Long accountId, @PathVariable Long walletId,
                                                   @RequestBody ApprovedTransactionDto dto) {
        log.info("Получен запрос patch /{}/wallet/{}", accountId, walletId);

        walletService.updateWallet(accountId, walletId, dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/wallet/types")
    public ResponseEntity<ResponseExchangeWalletsDto> getWallet(@RequestBody ReqExchangeWalletsDto dto) {
        log.info("Получен запрос post /wallet/types");
        log.debug("ReqExchangeWalletsDto: {}", dto);
        return ResponseEntity.ok(walletService.getWallet(dto));
    }

    @PatchMapping("/wallet/transfer")
    public void processTransferTransaction(@RequestBody ReqTransferMoneyDto dto) {
        log.info("Получен запрос patch /wallet/transfer");
        log.debug("ReqTransferMoneyDto: {}", dto);
        walletService.processTransferWallet(dto);
    }

    @PostMapping("/wallet/info")
    public ResponseEntity<Set<ResponseWalletDto>> getWalletInfoByUsername(@RequestBody ReqWalletInfoDto dto) {
        log.info("Получен запрос post /wallet/info");
        log.debug("ReqWalletInfoDto: {}", dto);
        return ResponseEntity.ok(walletService.getWalletInfo(dto));
    }
}
