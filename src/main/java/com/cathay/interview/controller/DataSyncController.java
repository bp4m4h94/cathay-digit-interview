package com.cathay.interview.controller;

import com.cathay.interview.dto.BitcoinPriceDataDto;
import com.cathay.interview.service.DataConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class DataSyncController {

    @Autowired
    private DataConverterService dataConverterService;

    @GetMapping("/convert")
    public BitcoinPriceDataDto convert() throws IOException {
        return dataConverterService.convert();
    }


}
