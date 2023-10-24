package com.cathay.interview.demo;

import com.cathay.interview.cathay.repository.CurrencyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class DemoApplicationTests {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Test
    void contextLoads() {
        currencyRepository.findAll();
    }

}
