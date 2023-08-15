package com.example.financing.model.response;

import lombok.Data;

@Data
public class BankError {
    private String code;
    private String message;
}
