package com.bankpay.remittance.application.port.in;

import com.bankpay.remittance.domain.RemittanceRequest;

import java.util.List;


public interface FindRemittanceUseCase {
    List<RemittanceRequest> findRemittanceHistory(FindRemittanceCommand command);
}
