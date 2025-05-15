package org.ivanov.account.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ivanov.account.service.AccountService;
import org.ivanov.account.service.WalletService;
import org.ivanov.accountdto.account.CreateAccountDto;
import org.ivanov.accountdto.account.ResponseAccountDto;
import org.ivanov.accountdto.account.UpdatePasswordDto;
import org.ivanov.accountdto.account.UpdateProfileDto;
import org.ivanov.accountdto.wallet.CreateWalletDto;
import org.ivanov.accountdto.wallet.ResponseWalletDto;
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
    public ResponseEntity<Void> deleteWallet(@PathVariable Long accountId, @PathVariable Long walletId) {
        walletService.deleteWallet(accountId, walletId);
        return ResponseEntity.noContent().build();
    }
}
