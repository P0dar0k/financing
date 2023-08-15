package com.example.financing.model.application;

import lombok.Data;

@Data
public class SolidBankLoanApplication {
    private String phone;
    private String email;
    private double monthlyIncome;
    private double monthlyExpenses;
    private MaritalStatus maritalStatus;
    private boolean agreeToBeScored;
    private double amount;
}
