package org.ivanov.front.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ivanov.accountdto.account.CreateAccountDto;
import org.ivanov.front.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

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
    public String register(@Valid @ModelAttribute CreateAccountDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        accountService.registration(dto);
        return "redirect:/login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
