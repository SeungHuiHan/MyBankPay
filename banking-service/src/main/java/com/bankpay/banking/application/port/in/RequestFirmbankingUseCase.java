package com.bankpay.banking.application.port.in;

import com.bankpay.banking.domain.FirmbankingRequest;


public interface RequestFirmbankingUseCase {
    FirmbankingRequest requestFirmbanking(RequestFirmbankingCommand command);
    void requestFirmbankingByEvent(RequestFirmbankingCommand command);

}
