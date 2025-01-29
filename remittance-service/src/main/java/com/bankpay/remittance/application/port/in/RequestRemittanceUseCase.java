package com.bankpay.remittance.application.port.in;

import com.bankpay.remittance.domain.RemittanceRequest;


public interface RequestRemittanceUseCase {
    RemittanceRequest requestRemittance(RequestRemittanceCommand command);
}
