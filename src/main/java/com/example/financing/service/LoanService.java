package com.example.financing.service;

import com.example.financing.model.application.LoanApplication;
import com.example.financing.model.response.SolidBankResponse;
import com.example.financing.model.response.SubmitApplicationResponse;

import java.util.List;

public interface LoanService {
    List<Object> applyForFinancing(LoanApplication application);

    SolidBankResponse retrieveApplication(String applicationId);

    SubmitApplicationResponse submitApplicationAsync(LoanApplication application);
}
