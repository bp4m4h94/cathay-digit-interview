package com.cathay.interview.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CurrencyInfoDto implements Serializable {

    private String code;
    private String symbol;
    private String rate;
    private String description;
    private BigDecimal rate_float;

}
