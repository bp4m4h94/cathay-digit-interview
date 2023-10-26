package com.cathay.interview.demo;

import com.cathay.interview.cathay.controller.CurrencyController;
import com.cathay.interview.cathay.entity.Currency;
import com.cathay.interview.remote.api.CoinDeskApi;
import com.cathay.interview.remote.dto.BitcoinPriceDataDto;
import com.cathay.interview.remote.dto.CurrencyInfoDto;
import com.cathay.interview.remote.enums.CurrencyType;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class DemoApplicationTests {

    @Autowired
    private CurrencyController controller;
    @Autowired
    private CoinDeskApi coinDeskService;

    @Test
    void search_code_api_content_correct() throws JsonProcessingException {
        Currency currency = controller.findByCode(CurrencyType.USD).getBody();
        BitcoinPriceDataDto deserialize = coinDeskService.deserialize();
        CurrencyInfoDto expected = deserialize.getBpi().get(CurrencyType.USD);
        assertEquals(expected.getCode(), currency != null ? currency.getCode().toString() : null);
    }

}
