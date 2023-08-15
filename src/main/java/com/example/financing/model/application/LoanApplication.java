package com.example.financing.model.application;

import lombok.Data;

@Data
public class LoanApplication {
    private String phoneNumber;
    private String email;
    private double monthlyIncomeAmount;
    private double monthlyCreditLiabilities;
    private MaritalStatus maritalStatus;
    private boolean agreeToDataSharing;
    private double amount;
    private int dependents;
}
