package com.cathay.interview.service;

import com.cathay.interview.dto.BitcoinPriceDataDto;

import java.io.IOException;

public interface DataConverterService {

    BitcoinPriceDataDto convert() throws IOException;
}
