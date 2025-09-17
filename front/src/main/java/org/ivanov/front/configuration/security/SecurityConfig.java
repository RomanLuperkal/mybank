package org.ivanov.front.configuration.security;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.ivanov.front.client.AccountClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final MeterRegistry meterRegistry;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register", "/actuator/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .failureHandler(failureHandler())
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login"))
                .sessionManagement(session -> session
                        .sessionFixation().migrateSession()
                        .maximumSessions(1)
                );

        return http.build();
    }

    @Bean
    public UserDetailsService accountUserDetailsService(AccountClient client, CircuitBreakerRegistry circuitBreakerRegistry) {
        return new AccountUserDetailService(client, circuitBreakerRegistry);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private AuthenticationFailureHandler failureHandler() {
        return (request, response, authException) -> {
            String redirectUrl = "/login";

            String login = request.getParameter("username");
            meterRegistry.counter("failure_login", "login", login).increment();

            if (authException instanceof BadCredentialsException) {
                redirectUrl += "?error";
            } else {
                String errorMessage = "Ошибка: " + authException.getMessage();
                String encodedMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);
                redirectUrl += "?alert=" + encodedMessage;
            }

            response.sendRedirect(redirectUrl);
        };
    }
}
