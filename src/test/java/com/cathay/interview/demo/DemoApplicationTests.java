package com.cathay.interview.demo;

import com.cathay.interview.cathay.controller.CurrencyController;
import com.cathay.interview.cathay.entity.Currency;
import com.cathay.interview.remote.api.CoinDeskApi;
import com.cathay.interview.remote.dto.BitcoinPriceDataDto;
import com.cathay.interview.remote.dto.CurrencyInfoDto;
import com.cathay.interview.remote.enums.CurrencyType;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
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

    @Test
    @DisplayName("測試呼叫查詢幣別對應表資料 API，並顯示其內容")
    void testFindNewApi() throws JsonProcessingException {
    }
    @Test
    @DisplayName("測試呼叫新增幣別對應表資料 API。")
    void testInsertNewApi() throws JsonProcessingException {
    }
    @Test
    @DisplayName("測試呼叫更新幣別對應表資料 API，並顯示其內容。")
    void testUpdateNewApi() throws JsonProcessingException {
    }
    @Test
    @DisplayName("測試呼叫刪除幣別對應表資料 API。")
    void testDeleteNewApi() throws JsonProcessingException {
    }
    @Test
    @DisplayName("測試呼叫 coindesk API，並顯示其內容")
    void testCoinDeskApi() throws JsonProcessingException {
    }
    @Test
    @DisplayName("測試呼叫資料轉換的 API，並顯示其內容")
    void testNewApi() throws JsonProcessingException {
    }

}
