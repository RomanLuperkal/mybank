package org.ivanov.front.configuration.security;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ivanov.accountdto.account.ResponseAccountDto;
import org.ivanov.front.client.AccountClient;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
@Slf4j
public class AccountUserDetailService implements UserDetailsService {

    private final AccountClient accountClient;
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logCircuitBreakerState();
        ResponseAccountDto account = accountClient.getAccount(username);
        logCircuitBreakerState();

        return AccountUserDetails.builder()
                .accountId(account.accountId())
                .username(account.username())
                .password(account.password())
                .wallets(account.wallets())
                .firstName(account.firstName())
                .lastName(account.lastName())
                .email(account.email())
                .dateOfBirth(account.birthDate())
                .authorities(AuthorityUtils.createAuthorityList(account.role()))
                .build();
    }

    public void logCircuitBreakerState() {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("front-circuitbreaker");
        CircuitBreaker.Metrics metrics = circuitBreaker.getMetrics();
        log.info("CircuitBreaker state: {}, Failure rate: {}%, Buffered calls: {}",
                circuitBreaker.getState(),
                metrics.getFailureRate(),
                metrics.getNumberOfBufferedCalls());
    }
}
