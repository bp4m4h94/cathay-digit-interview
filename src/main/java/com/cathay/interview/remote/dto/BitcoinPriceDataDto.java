package com.cathay.interview.remote.dto;

import com.cathay.interview.remote.enums.CurrencyCode;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class BitcoinPriceDataDto implements Serializable {

    private TimeInfoDto time;
    private String disclaimer;
    private String chartName;
    private Map<CurrencyCode, CurrencyInfoDto> bpi;
}
