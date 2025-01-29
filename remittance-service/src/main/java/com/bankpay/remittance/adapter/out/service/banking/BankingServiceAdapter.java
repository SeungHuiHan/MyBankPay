package com.bankpay.remittance.adapter.out.service.banking;

import com.bankpay.common.CommonHttpClient;
import com.bankpay.common.ExternalSystemAdapter;
import com.bankpay.remittance.application.port.out.banking.BankingInfo;
import com.bankpay.remittance.application.port.out.banking.BankingPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class BankingServiceAdapter implements BankingPort {
    private CommonHttpClient bankingServiceHttpClient;

    @Value("${service.banking.url}")
    private String bankingServiceEndpoint;

    public BankingServiceAdapter(CommonHttpClient bankingServiceHttpClient, String bankingServiceEndpoint) {
        this.bankingServiceHttpClient = bankingServiceHttpClient;
        this.bankingServiceEndpoint = bankingServiceEndpoint;
    }

    @Override
    public BankingInfo getMembershipBankingInfo(String bankName, String bankAccountNumber) {

        return null;
    }

    @Override
    public boolean requestFirmbanking(String bankName, String bankAccountNumber, int amount) {
        return false;
    }
}
