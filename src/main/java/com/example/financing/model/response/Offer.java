package com.example.financing.model.response;

import lombok.Data;

@Data
public class Offer {
    private Double monthlyPaymentAmount;
    private Double totalRepaymentAmount;
    private Integer numberOfPayments;
    private Double annualPercentageRate;
    private String firstRepaymentDate;
}