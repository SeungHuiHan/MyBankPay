package com.bankpay.money.moneyservice.adapter.out.persistence;

import com.bankpay.common.PersistenceAdapter;
import com.bankpay.money.moneyservice.application.port.out.IncreaseMoneyPort;
import com.bankpay.money.moneyservice.domain.MemberMoney;
import com.bankpay.money.moneyservice.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@PersistenceAdapter
@RequiredArgsConstructor
public class MoneyChangingRequestPersistenceAdapter implements IncreaseMoneyPort {

    private final SpringDataMoneyChangingRequestRepository moneyChangingRequestRepository;

    private final SpringDataMemberMoneyRepository memberMoneyRepository;

    @Override
    public MoneyChangingRequestJpaEntity createMoneyChangingMoney(MoneyChangingRequest.TargetMembershipId targetMembershipId, MoneyChangingRequest.MoneyChangingType moneyChangingType, MoneyChangingRequest.ChangingMoneyAmount changingMoneyAmount, MoneyChangingRequest.ChangingMoneyStatus changingMoneyStatus, MoneyChangingRequest.Uuid uuid) {
        return moneyChangingRequestRepository.save(
                new MoneyChangingRequestJpaEntity(
                        targetMembershipId.getTargetMembershipId(),
                        moneyChangingType.getMoneyChangingType(),
                        changingMoneyAmount.getChangingMoneyAmount(),
                        changingMoneyStatus.getChangingMoneyStatus(),
                        UUID.randomUUID(),
                        new Timestamp(System.currentTimeMillis())

                )
        );
    }

    @Override
    public MemberMoneyJpaEntity increaseMoney(MemberMoney.MembershipId memberId, int increaseAmount) {
        MemberMoneyJpaEntity entity;

        try {
            List<MemberMoneyJpaEntity> entityList=memberMoneyRepository.findByMembershipId(Long.parseLong(memberId.getMembershipId()));
            entity=entityList.get(0);

            entity.setBalance(entity.getBalance()+increaseAmount);
            return memberMoneyRepository.save(entity);
        }catch (Exception e){

            entity=new MemberMoneyJpaEntity(
                    memberId.getMembershipId(),
                    increaseAmount
            );
            return memberMoneyRepository.save(entity);

        }

//        entity.setBalance(entity.getBalance()+increaseAmount);
//        return memberMoneyRepository.save(entity);

    }
}
