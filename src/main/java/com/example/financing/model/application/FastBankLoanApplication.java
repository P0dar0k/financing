package com.example.financing.model.application;

import lombok.Data;

@Data
public class FastBankLoanApplication {
    private String phoneNumber;
    private String email;
    private double monthlyIncomeAmount;
    private double monthlyCreditLiabilities;
    private boolean agreeToDataSharing;
    private double amount;
    private int dependents;
}
