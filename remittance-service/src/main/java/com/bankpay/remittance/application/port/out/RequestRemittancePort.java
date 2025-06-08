package com.bankpay.remittance.application.port.out;

import com.bankpay.remittance.adapter.out.persistence.RemittanceRequestJpaEntity;
import com.bankpay.remittance.application.port.in.RequestRemittanceCommand;

public interface RequestRemittancePort {

    RemittanceRequestJpaEntity createRemittanceRequestHistory(RequestRemittanceCommand command);
    boolean saveRemittanceRequestHistory(RemittanceRequestJpaEntity entity);

}

