package org.ivanov.front.configuration.security;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ivanov.accountdto.account.ResponseAccountInfoDto;
import org.ivanov.front.client.AccountClient;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
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
        ResponseAccountInfoDto account = accountClient.getAccount(username);
        logCircuitBreakerState();

        return new User(account.username(), account.password(), AuthorityUtils.createAuthorityList(account.role()));
    }

    public void logCircuitBreakerState() {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("front-circuitbreaker");
        CircuitBreaker.Metrics metrics = circuitBreaker.getMetrics();
        log.info("CircuitBreaker state: {}, Failure rate: {}%, Buffered calls: {}",
                circuitBreaker.getState(),
                metrics.getFailureRate(),
               // metrics.getNumberOfBufferedCalls());
                metrics.getNumberOfBufferedCalls());
    }
}
