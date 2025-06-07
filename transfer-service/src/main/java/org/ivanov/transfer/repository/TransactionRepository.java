package org.ivanov.transfer.repository;

import org.ivanov.transfer.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findFirstByStatus(Transaction.Status status);
}
