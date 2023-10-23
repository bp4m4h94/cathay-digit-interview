package com.cathay.interview.dto;

import com.cathay.interview.CurrencyType;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class BitcoinPriceDataDto implements Serializable {

    private TimeInfoDto time;
    private String disclaimer;
    private String chartName;
    private Map<CurrencyType, CurrencyInfoDto> bpi;
}
