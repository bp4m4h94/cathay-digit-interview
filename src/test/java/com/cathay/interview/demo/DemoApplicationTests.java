package com.cathay.interview.demo;

import com.cathay.interview.cathay.controller.CurrencyController;
import com.cathay.interview.cathay.dto.model.CurrencyRequestDto;
import com.cathay.interview.cathay.dto.model.CurrencyResponseDto;
import com.cathay.interview.remote.api.CoinDeskApi;
import com.cathay.interview.remote.enums.CurrencyCode;
import org.hibernate.type.descriptor.DateTimeUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
class DemoApplicationTests {

    @Autowired
    private CurrencyController currencyController;
    @Autowired
    private CoinDeskApi coinDeskApi;

    @BeforeAll
    static void setUp(@Autowired CurrencyController currencyController) throws IOException {
        currencyController.transfer();
    }

    @Test
    @DisplayName("測試呼叫資料轉換的 API，並顯示其內容")
    void testNewApi() throws IOException {
        List<CurrencyResponseDto> body = currencyController.transfer().getBody();
        assertFalse(CollectionUtils.isEmpty(body));
        Optional<CurrencyResponseDto> optionalCurrencyResponseDto = body.stream()
                .filter(responseDto -> responseDto.getCode().equals(CurrencyCode.USD.name()))
                .findFirst();
        CurrencyResponseDto currencyResponseDto = optionalCurrencyResponseDto.orElse(new CurrencyResponseDto());
        assertEquals(CurrencyCode.USD.getChineseCode(), currencyResponseDto.getChineseCode());
    }

    @Test
    @DisplayName("測試呼叫查詢幣別對應表資料 API，並顯示其內容")
    void testFindNewApi() {
        CurrencyResponseDto body = currencyController.findByCode(CurrencyCode.GBP.name()).getBody();
        assertEquals(CurrencyCode.GBP.getChineseCode(), body.getChineseCode());
    }

    @Test
    @DisplayName("測試呼叫新增幣別對應表資料 API。")
    void testInsertNewApi() {
        CurrencyResponseDto body = currencyController.insert(
                new CurrencyRequestDto(
                        CurrencyCode.JPY.name(), 
                        CurrencyCode.JPY.getChineseCode(), 
                        new BigDecimal(20), 
                        null)).getBody();

        CurrencyResponseDto expected = CurrencyResponseDto.builder()
                .code(CurrencyCode.JPY.name())
                .chineseCode(CurrencyCode.JPY.getChineseCode())
                .rateFloat(new BigDecimal(20).toString())
                .updatedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateTimeUtils.FORMAT_STRING_TIMESTAMP)))
                .build();
        assertEquals(expected, body);
    }

    @Test
    @DisplayName("測試呼叫更新幣別對應表資料 API，並顯示其內容。")
    void testUpdateNewApi() {
    }

    @Test
    @DisplayName("測試呼叫刪除幣別對應表資料 API。")
    void testDeleteNewApi() {
    }

    @Test
    @DisplayName("測試呼叫 coindesk API，並顯示其內容")
    void testCoinDeskApi() {
        ResponseEntity<String> stringResponseEntity = coinDeskApi.downloadData();
        assertEquals(HttpStatus.OK, stringResponseEntity.getStatusCode());
    }
}
