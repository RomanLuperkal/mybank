package org.ivanov.account.repository;

import org.ivanov.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsAccountByUsername(String username);

    Optional<Account> findAccountByUsername(String username);
}
