package com.cathay.interview.cathay.controller;

import com.cathay.interview.cathay.dto.model.CurrencyRequestDto;
import com.cathay.interview.cathay.dto.model.CurrencyResponseDto;
import com.cathay.interview.cathay.entity.Currency;
import com.cathay.interview.cathay.repository.CurrencyRepository;
import com.cathay.interview.remote.api.CoinDeskApi;
import com.cathay.interview.remote.dto.BitcoinPriceDataDto;
import com.cathay.interview.remote.dto.CurrencyInfoDto;
import com.cathay.interview.remote.enums.CurrencyCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.type.descriptor.DateTimeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public ResponseEntity<CurrencyResponseDto> findByCode(@RequestParam @Validated String code) {
        Currency currency = currencyRepository.findByCode(code);
        if (currency != null) {
            CurrencyResponseDto responseDto = CurrencyResponseDto.builder()
                    .code(currency.getCode())
                    .chineseCode(currency.getChineseCode())
                    .rateFloat(currency.getRateFloat().toString())
                    .updatedDate(currency.getUpdatedDate())
                    .build();
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<CurrencyResponseDto> update(@RequestBody CurrencyRequestDto requestDto) {
        Currency currency = currencyRepository.findByCode(requestDto.getCode());
        if (currency == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BeanUtils.copyProperties(requestDto, currency);
        currency.setUpdatedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateTimeUtils.FORMAT_STRING_TIMESTAMP)));
        Currency updatedCurrency = currencyRepository.save(currency);
        CurrencyResponseDto responseDto = CurrencyResponseDto.builder()
                .code(updatedCurrency.getCode())
                .chineseCode(updatedCurrency.getChineseCode())
                .rateFloat(updatedCurrency.getRateFloat().toString())
                .updatedDate(updatedCurrency.getUpdatedDate())
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/find/all")
    public List<Currency> all() {
        return currencyRepository.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<CurrencyResponseDto> insert(@RequestBody CurrencyRequestDto requestDto) {
        Currency currency = Currency.builder()
                .code(requestDto.getCode())
                .chineseCode(requestDto.getChineseCode())
                .rateFloat(requestDto.getRateFloat())
                .updatedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateTimeUtils.FORMAT_STRING_TIMESTAMP))).build();
        Currency savedCurrency = currencyRepository.save(currency);
        CurrencyResponseDto responseDto = CurrencyResponseDto.builder()
                .code(savedCurrency.getCode())
                .chineseCode(savedCurrency.getChineseCode())
                .rateFloat(savedCurrency.getRateFloat().toString())
                .updatedDate(savedCurrency.getUpdatedDate())
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/delete/all")
    public void deleteAll() {
        currencyRepository.deleteAll();
    }

    @GetMapping("/transfer")
    public ResponseEntity<List<CurrencyResponseDto>> transfer() throws IOException {
        ResponseEntity<String> response = coinDeskApi.downloadData();
        BitcoinPriceDataDto bitcoinPriceDataDto = objectMapper.readValue(response.getBody(), BitcoinPriceDataDto.class);
        List<CurrencyInfoDto> bpiList = bitcoinPriceDataDto.getBpi().values().stream().toList();


        List<Currency> currencyArrayList = new ArrayList<>();
        for (CurrencyInfoDto source : bpiList) {
            Currency currencyHistory = currencyRepository.findByCode(source.getCode());
            Currency target = new Currency();
            if (currencyHistory != null) {
                target.setId(currencyHistory.getId());
            }
            target.setRateFloat(source.getRate_float());
            target.setUpdatedDate(getFormattedDate(bitcoinPriceDataDto.getTime().getUpdatedISO()));
            target.setCode(Enum.valueOf(CurrencyCode.class, source.getCode()).getEngCode());
            target.setChineseCode(Enum.valueOf(CurrencyCode.class, source.getCode()).getChineseCode());
            currencyArrayList.add(target);
        }
        List<CurrencyResponseDto> currencyResponseDtos = new ArrayList<>();

        List<Currency> currencies = currencyRepository.saveAll(currencyArrayList);
        for (Currency currency : currencies) {
            CurrencyResponseDto build = CurrencyResponseDto.builder()
                    .code(currency.getCode())
                    .chineseCode(currency.getChineseCode())
                    .rateFloat(currency.getRateFloat().toString())
                    .updatedDate(currency.getUpdatedDate())
                    .build();
            currencyResponseDtos.add(build);
        }
        return new ResponseEntity<>(currencyResponseDtos, HttpStatus.OK);
    }

    private static String getFormattedDate(String updatedDate) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        LocalDateTime dateTime = LocalDateTime.parse(updatedDate, inputFormatter);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(DateTimeUtils.FORMAT_STRING_TIMESTAMP);
        return dateTime.format(outputFormatter);
    }

}
