package com.bankpay.banking.application.service;

import com.bankpay.banking.adapter.out.external.bank.ExternalFirmbankingRequest;
import com.bankpay.banking.adapter.out.external.bank.FirmbankingResult;
import com.bankpay.banking.adapter.out.persistence.FirmbankingRequestJpaEntity;
import com.bankpay.banking.adapter.out.persistence.FirmbankingRequestMapper;
import com.bankpay.banking.application.port.in.FirmbankingRequestCommand;
import com.bankpay.banking.application.port.in.RequestFirmbankingUseCase;
import com.bankpay.banking.application.port.out.RequestExternalFirmbankingPort;
import com.bankpay.banking.application.port.out.RequestFirmbankingPort;
import com.bankpay.banking.domain.FirmbankingRequest;
import com.bankpay.common.UseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@UseCase
@RequiredArgsConstructor
@Transactional
public class RequestFirmbankingService implements RequestFirmbankingUseCase {
    private final RequestFirmbankingPort requestFirmbankingPort;
    private final FirmbankingRequestMapper requestMapper;
    private final RequestExternalFirmbankingPort requestExternalFirmbankingPort;

    @Override
    public FirmbankingRequest requestFirmbanking(FirmbankingRequestCommand command) {
        //Business Logic

        //a->b 계좌

        //1. 요청에 대해 정보를 먼저 write "요청"상대로
        FirmbankingRequestJpaEntity requestedEntity =requestFirmbankingPort.createFirmbankingRequest(
                new FirmbankingRequest.FromBankName(command.getFromBankName()),
                new FirmbankingRequest.FromBankAccountNumber(command.getFromBankAccountNumber()),
                new FirmbankingRequest.ToBankName(command.getToBankName()),
                new FirmbankingRequest.ToBankAccountNumber(command.getToBankAccountNumber()),
                new FirmbankingRequest.MoneyAmount(command.getMoneyAmount()),
                new FirmbankingRequest.FirmbankingStatus(0)
        );

        //2. 외부 은행에 펌뱅킹 요청
        FirmbankingResult result=requestExternalFirmbankingPort.requestExternalFirmbanking(new ExternalFirmbankingRequest(
                command.getFromBankName(),
                command.getFromBankAccountNumber(),
                command.getToBankName(),
                command.getToBankAccountNumber()
        ));

        //transactional UUID ,성공 실패 로깅 및 디버깅
        UUID randomUUID=UUID.randomUUID();
        requestedEntity.setUuid(randomUUID.toString());

        //3. 결과에 따라서 1번에서 작성했던 FirmbankibgRequest 정보를 Update
        if(result.getResultCode()==0){
            requestedEntity.setFirmbankingStatus(1);
        }else{
            requestedEntity.setFirmbankingStatus(2);
        }
        //4. 결과를 리턴하기 전에 바뀐 상태 값을 기준으로 다시 save
        //requestFirmbankingPort.modifyFirmbankingRequest(requestedEntity);

        return requestMapper.mapToDomainEntity(requestFirmbankingPort.modifyFirmbankingRequest(requestedEntity), randomUUID);
    }
}
