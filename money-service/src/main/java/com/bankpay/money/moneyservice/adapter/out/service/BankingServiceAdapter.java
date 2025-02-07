package com.bankpay.money.moneyservice.adapter.out.service;

import com.bankpay.common.CommonHttpClient;
import com.bankpay.money.moneyservice.application.port.out.GetRegisteredBankAccountPort;
import com.bankpay.money.moneyservice.application.port.out.RegisteredBankAccountAggregateIdentifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BankingServiceAdapter implements GetRegisteredBankAccountPort {

    private final CommonHttpClient commonHttpClient;

    private final String bankingServiceUrl;

    public BankingServiceAdapter(CommonHttpClient commonHttpClient,
                                 @Value("${service.banking.url}") String membershipServiceUrl) {
        this.commonHttpClient = commonHttpClient;
        this.bankingServiceUrl = membershipServiceUrl;
    }

    @Override
    public RegisteredBankAccountAggregateIdentifier getRegisteredBankAccount(String membershipId){
        String url = String.join("/", bankingServiceUrl, "banking/account", membershipId);
        try {
            System.out.println("url은 "+url);
            String jsonResponse = commonHttpClient.sendGetRequest(url).body();
            // json RegisteredBankAccount

            ObjectMapper mapper = new ObjectMapper();

            RegisteredBankAccount registeredBankAccount = mapper.readValue(jsonResponse, RegisteredBankAccount.class);
            System.out.println("BankingServiceAdapter의 아이덴 어쩌구"+registeredBankAccount.getAggregateIdentifier());
            return new RegisteredBankAccountAggregateIdentifier(
                    registeredBankAccount.getRegisteredBankAccountId()
                    , registeredBankAccount.getAggregateIdentifier()
                    , registeredBankAccount.getMembershipId()
                    , registeredBankAccount.getBankName()
                    , registeredBankAccount.getBankAccountNumber()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
