package org.ivanov.front.configuration.security;

import lombok.RequiredArgsConstructor;
import org.ivanov.accountdto.account.ResponseAccountInfoDto;
import org.ivanov.front.client.AccountClient;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class AccountUserDetailService implements UserDetailsService {

    private final AccountClient accountClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ResponseAccountInfoDto account = accountClient.getAccount(username);

        return new User(account.username(), account.password(), AuthorityUtils.createAuthorityList(account.role()));
    }
}
