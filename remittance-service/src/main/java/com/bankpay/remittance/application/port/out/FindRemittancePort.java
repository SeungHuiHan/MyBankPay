package com.bankpay.remittance.application.port.out;


import com.bankpay.remittance.adapter.out.persistence.RemittanceRequestJpaEntity;
import com.bankpay.remittance.application.port.in.FindRemittanceCommand;

import java.util.List;

public interface FindRemittancePort {
    List<RemittanceRequestJpaEntity> findRemittanceHistory(FindRemittanceCommand command);
}
