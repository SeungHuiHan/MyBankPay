package com.bankpay.remittance.application.service;

import com.bankpay.common.UseCase;
import com.bankpay.remittance.adapter.out.persistence.RemittanceRequestMapper;
import com.bankpay.remittance.application.port.in.FindRemittanceCommand;
import com.bankpay.remittance.application.port.in.FindRemittanceUseCase;
import com.bankpay.remittance.application.port.out.FindRemittancePort;
import com.bankpay.remittance.domain.RemittanceRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
@Transactional
public class FindRemittanceService implements FindRemittanceUseCase {
    private final FindRemittancePort findRemittancePort;
    private final RemittanceRequestMapper mapper;

    @Override
    public List<RemittanceRequest> findRemittanceHistory(FindRemittanceCommand command) {
        //
        findRemittancePort.findRemittanceHistory(command);
        return null;
    }
}
