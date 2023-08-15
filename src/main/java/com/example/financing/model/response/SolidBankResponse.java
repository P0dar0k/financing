package com.example.financing.model.response;

import com.example.financing.model.application.SolidBankLoanApplication;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
public class SolidBankResponse {
    private String id;
    private ApplicationStatus status;
    private Offer offer;
    private SolidBankLoanApplication request;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String timestamp;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<BankError> errors;
}
