package com.bankpay.money.moneyservice.adapter.out.persistence;

import com.bankpay.common.PersistenceAdapter;
import com.bankpay.money.moneyservice.application.port.in.CreateMemberMoneyPort;
import com.bankpay.money.moneyservice.application.port.in.GetMemberMoneyPort;
import com.bankpay.money.moneyservice.application.port.out.GetMemberMoneyListPort;
import com.bankpay.money.moneyservice.application.port.out.IncreaseMoneyPort;
import com.bankpay.money.moneyservice.domain.MemberMoney;
import com.bankpay.money.moneyservice.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@PersistenceAdapter
@RequiredArgsConstructor
public class MoneyChangingRequestPersistenceAdapter implements IncreaseMoneyPort, CreateMemberMoneyPort, GetMemberMoneyPort, GetMemberMoneyListPort {

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
    public MemberMoneyJpaEntity increaseMoney(MemberMoney.MembershipId memberId, int increaseMoneyAmount) {
        MemberMoneyJpaEntity entity;
        try {
            List<MemberMoneyJpaEntity> entityList =  memberMoneyRepository.findByMembershipId(Long.parseLong(memberId.getMembershipId()));
            entity = entityList.get(0);

            entity.setBalance(entity.getBalance() + increaseMoneyAmount);
            return  memberMoneyRepository.save(entity);
        } catch (Exception e){
            entity = new MemberMoneyJpaEntity(
                    Long.parseLong(memberId.getMembershipId()),
                    increaseMoneyAmount, ""
            );
            entity = memberMoneyRepository.save(entity);
            return entity;
        }

//        entity.setBalance(entity.getBalance()+increaseAmount);
//        return memberMoneyRepository.save(entity);

    }

    @Override
    public void createMemberMoney(MemberMoney.MembershipId memberId, MemberMoney.MoneyAggregateIdentifier aggregateIdentifier) {
        MemberMoneyJpaEntity entity = new MemberMoneyJpaEntity(
                Long.parseLong(memberId.getMembershipId()),
                        0, aggregateIdentifier.getAggregateIdentifier()
        );
        memberMoneyRepository.save(entity);
    }

    @Override
    public MemberMoneyJpaEntity getMemberMoney(MemberMoney.MembershipId memberId) {
        MemberMoneyJpaEntity entity;
        List<MemberMoneyJpaEntity> entityList =  memberMoneyRepository.findByMembershipId(Long.parseLong(memberId.getMembershipId()));
        if(entityList.size() == 0){
            entity = new MemberMoneyJpaEntity(
                    Long.parseLong(memberId.getMembershipId()),
                    0, ""
            );
            entity = memberMoneyRepository.save(entity);
            return entity;
        }
        return  entityList.get(0);
    }

    @Override
    public List<MemberMoneyJpaEntity> getMemberMoneyPort(List<String> membershipIds) {
        // membershipIds 를 기준으로, 여러개의 MemberMoneyJpaEntity 를 가져온다.
        return memberMoneyRepository.fineMemberMoneyListByMembershipIds(convertMembershipIds(membershipIds));
    }


    private List<Long> convertMembershipIds(List<String> membershipIds) {
        List<Long> longList = new ArrayList<>();
        // membershipIds 를 Long 타입의 List 로 변환한다.
        for(String membershipId : membershipIds) {
            longList.add(Long.parseLong(membershipId));
        }
        return longList;
    }
}
