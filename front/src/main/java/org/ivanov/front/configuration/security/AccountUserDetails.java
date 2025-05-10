package org.ivanov.front.configuration.security;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.ivanov.accountdto.wallet.ResponseWalletDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@Builder
public class AccountUserDetails implements UserDetails {
    private Long accountId;
    private String username;
    private String password;
    private Set<ResponseWalletDto> wallets;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}
