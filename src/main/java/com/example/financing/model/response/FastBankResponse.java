package com.example.financing.model.response;

import com.example.financing.model.application.FastBankLoanApplication;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
public class FastBankResponse {
    private String id;
    private ApplicationStatus status;
    private Offer offer;
    private FastBankLoanApplication request;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String timestamp;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<BankError> errors;
}
