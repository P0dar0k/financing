package com.example.financing.service;

import com.example.financing.model.application.FastBankLoanApplication;
import com.example.financing.model.application.LoanApplication;
import com.example.financing.model.application.SolidBankLoanApplication;
import com.example.financing.model.response.*;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class LoanApplicationService implements LoanService {
    @Value("${solid.bank.api.url}")
    private String solidBankUrl;

    @Value("${fast.bank.api.url}")
    private String fastBankUrl;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<Object> applyForFinancing(LoanApplication application) {
        List<Object> unifiedResponses = new ArrayList<>();
        String sbResponseJson = sendHttpRequest(solidBankUrl, mapToSolidBankApplication(application));
        if (sbResponseJson != null) {
            SolidBankResponse solidBankResponse = mapJsonToClass(sbResponseJson, SolidBankResponse.class);
            unifiedResponses.add(solidBankResponse);
        }

        String fbResponseJson = sendHttpRequest(fastBankUrl, mapToFastBankApplication(application));
        if (fbResponseJson != null) {
            FastBankResponse fastBankResponse = mapJsonToClass(fbResponseJson, FastBankResponse.class);
            unifiedResponses.add(fastBankResponse);
        }

        return unifiedResponses;
    }

    public SolidBankResponse retrieveApplication(String applicationId) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(solidBankUrl + "/" + applicationId);

            HttpResponse response = httpClient.execute(httpGet);
            String responseJson = EntityUtils.toString(response.getEntity());

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(responseJson, SolidBankResponse.class);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public SubmitApplicationResponse submitApplicationAsync(LoanApplication application) {
        CompletableFuture<FastBankResponse> fastBankResponseFuture = CompletableFuture.supplyAsync(() -> applyForFinancingFastBank(application));
        CompletableFuture<SolidBankResponse> solidBankResponseFuture = CompletableFuture.supplyAsync(() -> applyForFinancingSolidBank(application));

        CompletableFuture<Void> allOf = CompletableFuture.allOf(fastBankResponseFuture, solidBankResponseFuture);

        try {
            allOf.get(); // Wait for both responses to be available
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }

        FastBankResponse fastBankResponse = fastBankResponseFuture.join();
        SolidBankResponse solidBankResponse = solidBankResponseFuture.join();

        // can be solidbank too
        String applicationId = fastBankResponse.getId();

        Offer fastBankOffer = fastBankResponse.getOffer();
        Offer solidBankOffer = solidBankResponse.getOffer();

        return new SubmitApplicationResponse(applicationId, fastBankOffer, solidBankOffer);
    }

    private FastBankResponse applyForFinancingFastBank(LoanApplication application) {
        String fbResponseJson = sendHttpRequest(fastBankUrl, mapToFastBankApplication(application));
        if (fbResponseJson != null) {
            return mapJsonToClass(fbResponseJson, FastBankResponse.class);
        }
        return null;
    }

    private SolidBankResponse applyForFinancingSolidBank(LoanApplication application) {
        String sbResponseJson = sendHttpRequest(solidBankUrl, mapToSolidBankApplication(application));
        if (sbResponseJson != null) {
            return mapJsonToClass(sbResponseJson, SolidBankResponse.class);
        }
        return null;
    }

    private static FastBankLoanApplication mapToFastBankApplication(LoanApplication application) {
        FastBankLoanApplication fbApplication = new FastBankLoanApplication();
        fbApplication.setPhoneNumber(application.getPhoneNumber());
        fbApplication.setEmail(application.getEmail());
        fbApplication.setMonthlyIncomeAmount(application.getMonthlyIncomeAmount());
        fbApplication.setMonthlyCreditLiabilities(application.getMonthlyCreditLiabilities());
        fbApplication.setAgreeToDataSharing(application.isAgreeToDataSharing());
        fbApplication.setAmount(application.getAmount());
        fbApplication.setDependents(application.getDependents());
        return fbApplication;
    }

    private static SolidBankLoanApplication mapToSolidBankApplication(LoanApplication application) {
        SolidBankLoanApplication sbApplication = new SolidBankLoanApplication();
        sbApplication.setPhone(application.getPhoneNumber());
        sbApplication.setEmail(application.getEmail());
        sbApplication.setMonthlyIncome(application.getMonthlyIncomeAmount());
        sbApplication.setMonthlyExpenses(application.getMonthlyCreditLiabilities());
        sbApplication.setMaritalStatus(application.getMaritalStatus());
        sbApplication.setAgreeToBeScored(application.isAgreeToDataSharing());
        sbApplication.setAmount(application.getAmount());
        return sbApplication;
    }

    private String sendHttpRequest(String url, Object requestBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(objectMapper.writeValueAsString(requestBody)));

            HttpResponse response = httpClient.execute(httpPost);
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T mapJsonToClass(String json, Class<T> targetClass) {
        try {
            return objectMapper.readValue(json, targetClass);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
