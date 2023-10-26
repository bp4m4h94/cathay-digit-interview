package com.cathay.interview.cathay.controller;

import com.cathay.interview.remote.dto.BitcoinPriceDataDto;
import com.cathay.interview.remote.dto.CurrencyInfoDto;
import com.cathay.interview.cathay.entity.Currency;
import com.cathay.interview.remote.api.CoinDeskApi;
import com.cathay.interview.cathay.repository.CurrencyRepository;
import com.cathay.interview.remote.enums.CurrencyType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<Currency> findByCode(@RequestParam @Validated CurrencyType code) {
        Currency currency = currencyRepository.findByCode(code);
        if (currency != null) {
            return new ResponseEntity<>(currency, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/updated-date")
    public ResponseEntity<String> findUpdatedDateByCode() throws JsonProcessingException {
        ResponseEntity<String> response = coinDeskApi.downloadData();
        BitcoinPriceDataDto bitcoinPriceDataDto = objectMapper.readValue(response.getBody(), BitcoinPriceDataDto.class);
        String updatedDate = bitcoinPriceDataDto.getTime().getUpdated();
        if (updatedDate != null) {
            return new ResponseEntity<>(updatedDate, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/sync")
    public List<Currency> sync() throws IOException {
        ResponseEntity<String> response = coinDeskApi.downloadData();
        BitcoinPriceDataDto bitcoinPriceDataDto = objectMapper.readValue(response.getBody(), BitcoinPriceDataDto.class);
        List<CurrencyInfoDto> bpiList = bitcoinPriceDataDto.getBpi().values().stream().toList();

        List<Currency> currencyArrayList = new ArrayList<>();
        for (CurrencyInfoDto source : bpiList) {
            Currency target = currencyRepository.findByCode(Enum.valueOf(CurrencyType.class, source.getCode()));
            target.setSymbol(source.getSymbol());
            target.setRateFloat(source.getRate_float());
            target.setDescription(source.getDescription());
            target.setUpdatedDate(bitcoinPriceDataDto.getTime().getUpdated());
            currencyArrayList.add(target);
        }
        return currencyRepository.saveAll(currencyArrayList);
    }

    @GetMapping("/all")
    public List<Currency> all() {
        return currencyRepository.findAll();
    }

}
