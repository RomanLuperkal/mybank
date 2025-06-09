package org.ivanov.account.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.blog.cashdto.transaction.ApprovedTransactionDto;
import org.ivanov.account.service.AccountService;
import org.ivanov.account.service.WalletService;
import org.ivanov.accountdto.account.CreateAccountDto;
import org.ivanov.accountdto.account.ResponseAccountDto;
import org.ivanov.accountdto.account.UpdatePasswordDto;
import org.ivanov.accountdto.account.UpdateProfileDto;
import org.ivanov.accountdto.wallet.CreateWalletDto;
import org.ivanov.accountdto.wallet.ResponseExchangeWalletsDto;
import org.ivanov.accountdto.wallet.ResponseWalletDto;
import org.ivanov.transferdto.ReqExchangeWalletsDto;
import org.ivanov.transferdto.ReqTransferMoneyDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final WalletService walletService;

    @PostMapping("/registration")
    public ResponseEntity<ResponseAccountDto> registry(@Valid @RequestBody CreateAccountDto createAccountDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(createAccountDto));
    }

    @GetMapping("{username}")
    public ResponseEntity<ResponseAccountDto> getAccountInfo(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAccountInfo(username));
    }

    @DeleteMapping("{accountId}/delete-account")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable long accountId) {
        accountService.deleteAccount(accountId);
    }

    @PatchMapping("/{accountId}/change-password")
    @ResponseStatus(HttpStatus.OK)
    public void updatePassword(@PathVariable Long accountId,
                               @RequestBody UpdatePasswordDto newPassword) {
        accountService.updatePassword(accountId, newPassword);
    }

    @PatchMapping("/{accountId}/update-profile")
    @ResponseStatus(HttpStatus.OK)
    public void updateProfile(@PathVariable Long accountId, @RequestBody UpdateProfileDto dto) {
        accountService.updateProfile(accountId, dto);
    }

    @PostMapping("/{accountId}/wallet")
    public ResponseEntity<ResponseWalletDto> createWallet(@PathVariable Long accountId,
                                                          @Valid @RequestBody CreateWalletDto createWalletDto) {
        return ResponseEntity.ok(walletService.createWallet(accountId, createWalletDto));
    }

    @DeleteMapping("/{accountId}/wallet/{walletId}")
    public ResponseEntity<ResponseWalletDto> deleteWallet(@PathVariable Long accountId, @PathVariable Long walletId) {
        return ResponseEntity.ok(walletService.deleteWallet(accountId, walletId));
    }

    @PatchMapping("/{accountId}/wallet/{walletId}")
    public ResponseEntity<Void> processTransaction(@PathVariable Long accountId, @PathVariable Long walletId,
                                                   @RequestBody ApprovedTransactionDto dto) {
        walletService.updateWallet(accountId, walletId, dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/wallet/types")
    public ResponseEntity<ResponseExchangeWalletsDto> getWallet(@RequestBody ReqExchangeWalletsDto dto) {
        return ResponseEntity.ok(walletService.getWallet(dto));
    }

    @PatchMapping("/wallet/transfer")
    public void processTransferTransaction(@RequestBody ReqTransferMoneyDto dto) {
        walletService.processTransferWallet(dto);
    }
}
