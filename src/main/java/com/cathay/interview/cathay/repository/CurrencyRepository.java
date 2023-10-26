package com.cathay.interview.cathay.repository;

import com.cathay.interview.cathay.entity.Currency;
import com.cathay.interview.remote.enums.CurrencyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Currency findByCode(CurrencyType code);
}
