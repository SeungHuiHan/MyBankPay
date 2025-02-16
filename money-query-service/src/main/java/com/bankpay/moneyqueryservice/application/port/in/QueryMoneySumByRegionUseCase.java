package com.bankpay.moneyqueryservice.application.port.in;

import com.bankpay.moneyqueryservice.domain.MoneySumByRegion;

public interface QueryMoneySumByRegionUseCase {

    MoneySumByRegion queryMoneySumByRegion (QueryMoneySumByRegionQuery query);
}