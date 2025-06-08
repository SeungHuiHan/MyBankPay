package com.bankpay.moneyqueryservice.application.service;

import com.bankpay.common.UseCase;
import com.bankpay.moneyqueryservice.adapter.axon.QueryMoneySumByAddress;
import com.bankpay.moneyqueryservice.application.port.in.QueryMoneySumByRegionQuery;
import com.bankpay.moneyqueryservice.application.port.in.QueryMoneySumByRegionUseCase;
import com.bankpay.moneyqueryservice.domain.MoneySumByRegion;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryGateway;

import java.util.concurrent.ExecutionException;

@UseCase
@RequiredArgsConstructor
@Transactional
public class QueryMoneySumByRegionService implements QueryMoneySumByRegionUseCase {
    private final QueryGateway queryGateway;
    @Override
    public MoneySumByRegion queryMoneySumByRegion(QueryMoneySumByRegionQuery query) {
        try {
            return queryGateway.query(new QueryMoneySumByAddress(query.getAddress())
                    , MoneySumByRegion.class).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
