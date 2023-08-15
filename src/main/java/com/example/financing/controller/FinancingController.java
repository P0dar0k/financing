package com.example.financing.controller;

import com.example.financing.model.application.LoanApplication;
import com.example.financing.model.response.SolidBankResponse;
import com.example.financing.model.response.SubmitApplicationResponse;
import com.example.financing.service.LoanApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FinancingController {
    private final LoanApplicationService loanApplicationService;

    @Autowired
    public FinancingController(LoanApplicationService loanApplicationService) {
        this.loanApplicationService = loanApplicationService;
    }

    @PostMapping("/apply")
    public ResponseEntity<List<Object>> applyForFinancing(@RequestBody LoanApplication application) {
        List<Object> responseMap = loanApplicationService.applyForFinancing(application);
        return ResponseEntity.ok(responseMap);
    }

    @PostMapping("/applyAsync")
    public ResponseEntity<SubmitApplicationResponse> applyForFinancingAsync(@RequestBody LoanApplication application) {
        SubmitApplicationResponse response = loanApplicationService.submitApplicationAsync(application);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/applications/{id}")
    public ResponseEntity<SolidBankResponse> retrieveApplication(@PathVariable String id) {
        SolidBankResponse retrievedApplication = loanApplicationService.retrieveApplication(id);
        if (retrievedApplication != null) {
            return ResponseEntity.ok(retrievedApplication);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
