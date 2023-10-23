package com.cathay.interview.adapter;

import com.cathay.interview.dto.BitcoinPriceDataDto;
import com.cathay.interview.entity.Currency;
import com.cathay.interview.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DtoToEntityAdapter {
    @Autowired
    private CurrencyRepository currencyRepository;

    public Currency adapt(BitcoinPriceDataDto bitcoinPriceDataDto) {
//        bitcoinPriceDataDto.getBpi()
        Currency currency = new Currency();
        List<Currency> currencyListTarget = currencyRepository.findAll();
//        Currency.builder()
//                .code()
//                .symbol()
//                .description()

        return currency;
    }
}
