package com.cathay.interview.cathay.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Currency implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "code")
    private String code;
    @Column(name = "code_ch")
    private String chineseCode;
    @Column(name = "symbol")
    private String symbol;
    @Column(name = "description")
    private String description;
    @Column(name = "rate_float")
    private BigDecimal rateFloat;
    @Column(name = "updated_date")
    private String updatedDate;
}