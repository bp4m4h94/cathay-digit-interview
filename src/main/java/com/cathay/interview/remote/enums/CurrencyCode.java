package com.cathay.interview.remote.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CurrencyCode {
    USD("USD", "美金"),
    GBP("GBP", "英鎊"),
    EUR("EUR", "歐元"),
    JPY("JPY", "日圓");
    private final String engCode;
    private final String chineseCode;
}
