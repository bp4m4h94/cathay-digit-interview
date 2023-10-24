package com.cathay.interview.controller;

import com.cathay.interview.dto.BitcoinPriceDataDto;
import com.cathay.interview.dto.CurrencyInfoDto;
import com.cathay.interview.entity.Currency;
import com.cathay.interview.remote.CoinDeskApi;
import com.cathay.interview.repository.CurrencyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/currency")
public class CurrencyController {
    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private CoinDeskApi coinDeskApi;
    @Autowired
    private ObjectMapper objectMapper;


    @GetMapping("/all")
    public List<Currency> all() {
        return currencyRepository.findAll();
    }

    @GetMapping("/sync")
    public List<Currency> sync() throws IOException {
        ResponseEntity<String> response = coinDeskApi.downloadData();
        BitcoinPriceDataDto bitcoinPriceDataDto = objectMapper.readValue(response.getBody(), BitcoinPriceDataDto.class);
        List<CurrencyInfoDto> bpiList = bitcoinPriceDataDto.getBpi().values().stream().toList();

        List<Currency> currencyArrayList = new ArrayList<>();
        for (CurrencyInfoDto source : bpiList) {
            Currency target = currencyRepository.findByCode(source.getCode());
            target.setSymbol(source.getSymbol());
            target.setRateFloat(source.getRate_float());
            target.setDescription(source.getDescription());
            target.setUpdatedDate(bitcoinPriceDataDto.getTime().getUpdated());
            currencyArrayList.add(target);
        }
        return currencyRepository.saveAll(currencyArrayList);
    }
}
