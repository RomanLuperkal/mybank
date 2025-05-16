package org.ivanov.front.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ivanov.accountdto.account.CreateAccountDto;
import org.ivanov.accountdto.account.ResponseAccountDto;
import org.ivanov.accountdto.account.UpdatePasswordDto;
import org.ivanov.accountdto.account.UpdateProfileDto;
import org.ivanov.accountdto.wallet.CreateWalletDto;
import org.ivanov.accountdto.wallet.ResponseWalletDto;
import org.ivanov.front.configuration.security.AccountUserDetails;
import org.ivanov.front.service.AccountService;
import org.ivanov.front.service.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@Controller
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final WalletService walletService;

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Неверные логин или пароль");
        }
        return "login";
    }

    @GetMapping("/register")
    public String register(@ModelAttribute("createAccountDto") CreateAccountDto dto) {
        return "registration";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute CreateAccountDto dto,
                           BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        ResponseAccountDto accountDto = accountService.registration(dto);
        authUser(accountDto, request);
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Authentication authentication, Model model) {
        AccountUserDetails userDetails = getAccountUserDetails(authentication);
        model.addAttribute("user", userDetails);
        return "home";
    }

    @GetMapping("account/settings")
    public String accountSettings(Authentication authentication, Model model) {
        AccountUserDetails userDetails = getAccountUserDetails(authentication);
        //userDetails.setWallets(Set.of(new ResponseWalletDto("RUB",  BigDecimal.ONE), new ResponseWalletDto("USD",  new BigDecimal("2.2"))));
        model.addAttribute("user", userDetails);
        return "account-settings";
    }

    @DeleteMapping("/account/{accountId}/delete-account")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable Long accountId, Authentication authentication, Model model) {
        AccountUserDetails userDetails = getAccountUserDetails(authentication);
        accountService.deleteAccount(accountId, userDetails.getWallets());
    }

    @PatchMapping("/account/{accountId}/change-password")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@PathVariable Long accountId, Authentication authentication,
                               @RequestBody UpdatePasswordDto newPassword) {
        String updatedPassword = accountService.updatePassword(accountId, newPassword);
        getAccountUserDetails(authentication).setPassword(updatedPassword);
    }
    @PatchMapping("/account/{accountId}/update-profile")
    public ResponseEntity<Void> updateProfile(@PathVariable Long accountId, Authentication authentication, @Valid @RequestBody UpdateProfileDto dto) {
        accountService.updateProfile(accountId, dto);
        AccountUserDetails userDetails = getAccountUserDetails(authentication);
        updateUserProfile(userDetails, dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("account/{accountId}/wallet")
    public ResponseEntity<?> createWallet(@PathVariable Long accountId,
                                                          @Valid @RequestBody CreateWalletDto createWalletDto,
                                                          Authentication authentication, Model model) {
        AccountUserDetails userDetails = getAccountUserDetails(authentication);
        ResponseWalletDto createdWallet = walletService.createWallet(accountId, createWalletDto, userDetails.getWallets());
        userDetails.getWallets().add(createdWallet);
        model.addAttribute("user", userDetails);
        return ResponseEntity.ok(createdWallet);
    }

    @DeleteMapping("account/{accountId}/wallet/{walletId}")
    public ResponseEntity<Void> deleteWallet(@PathVariable Long accountId, @PathVariable Long walletId,
                                             Authentication authentication, Model model) {
        AccountUserDetails userDetails = getAccountUserDetails(authentication);
        //TODO необходимо репсонс удаленного счета
        walletService.deleteWallet(accountId, walletId, userDetails.getWallets());
        //userDetails.getWallets().remove();
        return ResponseEntity.noContent().build();
    }


    private void updateUserProfile(AccountUserDetails userDetails, UpdateProfileDto dto) {
        userDetails.setEmail(dto.email());
        userDetails.setFirstName(dto.firstName());
        userDetails.setLastName(dto.lastName());
        userDetails.setDateOfBirth(dto.birthDate());
    }

    private void authUser(ResponseAccountDto accountDto, HttpServletRequest request) {
        AccountUserDetails newUser = AccountUserDetails.builder()
                .accountId(accountDto.accountId())
                .username(accountDto.username())
                .password(accountDto.password())
                .wallets(accountDto.wallets())
                .firstName(accountDto.firstName())
                .lastName(accountDto.lastName())
                .email(accountDto.email())
                .dateOfBirth(accountDto.birthDate())
                .authorities(AuthorityUtils.createAuthorityList(accountDto.role()))
                .build();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                newUser, null, newUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());
    }

    private AccountUserDetails getAccountUserDetails(Authentication authentication) {
        return (AccountUserDetails) authentication.getPrincipal();
    }
}
