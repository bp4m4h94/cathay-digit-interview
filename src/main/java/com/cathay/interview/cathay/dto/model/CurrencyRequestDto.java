package com.cathay.interview.cathay.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CurrencyRequestDto implements Serializable {

    private String code;
    private String chineseCode;
    private BigDecimal rateFloat;
    private String updatedDate;

}
