package com.example.financing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LoanApplicationController {

    @GetMapping
    public String showLoanApplicationForm() {
        return "loan-application";
    }
}
