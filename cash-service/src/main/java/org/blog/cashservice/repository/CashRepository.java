package org.blog.cashservice.repository;

import org.blog.cashservice.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashRepository extends JpaRepository<Transaction, Long> {
    Transaction findFirstByStatus(Transaction.Status status);
}
