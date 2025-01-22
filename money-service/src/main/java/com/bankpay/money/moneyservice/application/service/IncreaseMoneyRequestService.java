package com.bankpay.money.moneyservice.application.service;

import com.bankpay.common.UseCase;
import com.bankpay.money.moneyservice.adapter.out.persistence.MemberMoneyJpaEntity;
import com.bankpay.money.moneyservice.adapter.out.persistence.MoneyChangingRequestMapper;
import com.bankpay.money.moneyservice.application.port.in.IncreaseMoneyRequestCommand;
import com.bankpay.money.moneyservice.application.port.in.IncreaseMoneyRequestUseCase;
import com.bankpay.money.moneyservice.application.port.out.IncreaseMoneyPort;
import com.bankpay.money.moneyservice.domain.MemberMoney;
import com.bankpay.money.moneyservice.domain.MoneyChangingRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@UseCase
@RequiredArgsConstructor
@Transactional
public class IncreaseMoneyRequestService implements IncreaseMoneyRequestUseCase {
    private final IncreaseMoneyPort increaseMoneyPort;
    private final MoneyChangingRequestMapper mapper;

    @Override
    public MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command) {

        //머니의 충전, 증액이라는 과정
        //1. 고객 정보가 정상인지 확인 (멤버)

        //2. 고객의 연동된 계좌가 있는지, 고객의 연동된 계좌의 잔액이 충분한지도 확인 (뱅킹)

        //3. 법인 계좌 상태도 정상인지 확인 (뱅킹)

        //4. 증액을 위한 "기록", 요청 상태로 MoneyChangingRequest 를 생성한다. (MoneyChangingRequest 저장)

        //5. 펌뱅킹을 수정하고 (고객의 연동된 계좌 -> 뱅킹페이 법인 계좌) (뱅킹)

        //6-1. 결과가 정상적이라면, 성공으로 MpneyChangingRequest 상태값을 변동 후에 리턴

        //성공 시에 멤버의 MemeberMoney 값 증액이 필요
        MemberMoneyJpaEntity memberMoneyJpaEntity =increaseMoneyPort.increaseMoney(
                new MemberMoney.MembershipId(command.getTargetMembershipId()),
                command.getAmount());

        if(memberMoneyJpaEntity!=null){
            return mapper.mapToDomainEntity(increaseMoneyPort.createMoneyChangingMoney(
                    new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
                    new MoneyChangingRequest.MoneyChangingType(1),
                    new MoneyChangingRequest.ChangingMoneyAmount(command.getAmount()),
                    new MoneyChangingRequest.ChangingMoneyStatus(1),
                    new MoneyChangingRequest.Uuid(UUID.randomUUID())
            ));
        }
        //6-2. 결과가 실패라면, 실패라고 MoneyChangingRequest 상태값을 변동 후에 리턴


        return null;
    }
}
