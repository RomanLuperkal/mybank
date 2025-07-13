package org.ivanov.front.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Controller
@RequiredArgsConstructor
@Slf4j
public class AccountController {
    private final AccountService accountService;
    private final WalletService walletService;

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, Model model) {
        log.info("get login request");
        if (error != null) {
            model.addAttribute("error", "Неверные логин или пароль");
        }
        return "login";
    }

    @GetMapping("/register")
    public String register(@ModelAttribute("createAccountDto") CreateAccountDto dto) {
        log.info("get register request");
        return "registration";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute CreateAccountDto dto,
                           BindingResult bindingResult, HttpServletRequest request) {
        log.info("Регистрация пользователя: {}", dto);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        ResponseAccountDto accountDto = accountService.registration(dto);
        authUser(accountDto, request);
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Authentication authentication, Model model) {
        log.info("get home request");
        model.addAttribute("user", accountService.getAccountInfo(getUsername(authentication)));
        return "home";
    }

    @GetMapping("account/settings")
    public String accountSettings(Authentication authentication, Model model) {
        log.info("get settings request");
        model.addAttribute("user", accountService.getAccountInfo(getUsername(authentication)));
        return "account-settings";
    }

    @DeleteMapping("/account/{accountId}/delete-account")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable Long accountId, Authentication authentication) {
        log.info("Удаление аккаунта с id={}", accountId);
        accountService.deleteAccount(accountId, accountService.getAccountInfo(getUsername(authentication)).wallets());
    }

    @PatchMapping("/account/{accountId}/change-password")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@PathVariable Long accountId, @RequestBody UpdatePasswordDto newPassword) {
        log.info("Изменения пароля для аккаунта с id={}", accountId);
        accountService.updatePassword(accountId, newPassword);
    }
    @PatchMapping("/account/{accountId}/update-profile")
    public ResponseEntity<Void> updateProfile(@PathVariable Long accountId, @Valid @RequestBody UpdateProfileDto dto) {
        log.info("Обновление профиля с id={} updateProfileDtp={}", accountId, dto);
        accountService.updateProfile(accountId, dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("account/{accountId}/wallet")
    public ResponseEntity<?> createWallet(@PathVariable Long accountId,
                                                          @Valid @RequestBody CreateWalletDto createWalletDto,
                                                          Authentication authentication, Model model) {
        log.info("Cоздание счета - {} для аккаунта с id={}", createWalletDto, accountId);
        ResponseWalletDto createdWallet = walletService.createWallet(accountId, createWalletDto, accountService
                .getAccountInfo(getUsername(authentication)).wallets());
        model.addAttribute("user", accountService.getAccountInfo(getUsername(authentication)));
        return ResponseEntity.ok(createdWallet);
    }

    @DeleteMapping("account/{accountId}/wallet/{walletId}")
    public ResponseEntity<ResponseWalletDto> deleteWallet(@PathVariable Long accountId, @PathVariable Long walletId,
                                             Authentication authentication) {
        log.info("Удаление счета с id= {} для аккаунта с id={}", walletId, accountId);
        ResponseWalletDto responseWalletDto = walletService.deleteWallet(accountId, walletId,
                accountService.getAccountInfo(getUsername(authentication)).wallets());
        return ResponseEntity.ok(responseWalletDto);
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
                .dateOfBirth(accountDto.dateOfBirth())
                .authorities(AuthorityUtils.createAuthorityList(accountDto.role()))
                .build();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                newUser, null, newUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());
    }

    private String getUsername(Authentication authentication) {
        AccountUserDetails principal = (AccountUserDetails) authentication.getPrincipal();
        return principal.getUsername();
    }
}
