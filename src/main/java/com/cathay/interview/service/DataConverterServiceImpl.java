package com.cathay.interview.service;

import com.cathay.interview.dto.BitcoinPriceDataDto;
import com.cathay.interview.remote.CoinDeskApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DataConverterServiceImpl implements DataConverterService {

    @Autowired
    private CoinDeskApi coinDeskApi;
    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public BitcoinPriceDataDto convert() throws IOException {
        ResponseEntity<String> response = coinDeskApi.downloadData();
        return objectMapper.readValue(response.getBody(), BitcoinPriceDataDto.class);
    }
}
