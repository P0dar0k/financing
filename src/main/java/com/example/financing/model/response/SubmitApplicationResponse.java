package com.example.financing.model.response;

import lombok.Data;

@Data
public class SubmitApplicationResponse {
    private String applicationId;
    private Offer fastBankOffer;
    private Offer solidBankOffer;

    public SubmitApplicationResponse(String applicationId, Offer fastBankOffer, Offer solidBankOffer) {
        this.applicationId = applicationId;
        this.fastBankOffer = fastBankOffer;
        this.solidBankOffer = solidBankOffer;
    }
}
