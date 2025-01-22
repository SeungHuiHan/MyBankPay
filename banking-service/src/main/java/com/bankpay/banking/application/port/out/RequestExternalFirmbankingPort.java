package com.bankpay.banking.application.port.out;

import com.bankpay.banking.adapter.out.external.bank.ExternalFirmbankingRequest;
import com.bankpay.banking.adapter.out.external.bank.FirmbankingResult;

public interface RequestExternalFirmbankingPort {
    public FirmbankingResult requestExternalFirmbanking(ExternalFirmbankingRequest request);
}
