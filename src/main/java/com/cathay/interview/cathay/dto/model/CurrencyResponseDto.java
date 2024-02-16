package com.cathay.interview.cathay.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CurrencyResponseDto implements Serializable {

    private String code;
    private String chineseCode;
    private String rateFloat;
    private String updatedDate;

}
