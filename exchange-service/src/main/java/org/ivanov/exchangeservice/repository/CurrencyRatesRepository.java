package org.ivanov.exchangeservice.repository;

import org.ivanov.exchangeservice.model.CurrencyRates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRatesRepository extends JpaRepository<CurrencyRates, Long> {
}
