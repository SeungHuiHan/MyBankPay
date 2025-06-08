package com.bankpay.money.moneyservice.application.service;

import com.bankpay.common.CountDownLatchManager;
import com.bankpay.common.RechargingMoneyTask;
import com.bankpay.common.SubTask;
import com.bankpay.common.UseCase;
import com.bankpay.money.moneyservice.adapter.axon.command.MemberMoneyCreatedCommand;
import com.bankpay.money.moneyservice.adapter.axon.command.RechargingMoneyRequestCreateCommand;
import com.bankpay.money.moneyservice.adapter.out.persistence.MemberMoneyJpaEntity;
import com.bankpay.money.moneyservice.adapter.out.persistence.MemberMoneyMapper;
import com.bankpay.money.moneyservice.adapter.out.persistence.MoneyChangingRequestMapper;
import com.bankpay.money.moneyservice.application.port.in.*;
import com.bankpay.money.moneyservice.application.port.out.GetMemberMoneyListPort;
import com.bankpay.money.moneyservice.application.port.out.GetMembershipPort;
import com.bankpay.money.moneyservice.application.port.out.IncreaseMoneyPort;
import com.bankpay.money.moneyservice.application.port.out.SendRechargingMoneyTaskPort;
import com.bankpay.money.moneyservice.domain.MemberMoney;
import com.bankpay.money.moneyservice.domain.MoneyChangingRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@UseCase
@RequiredArgsConstructor
@Transactional
public class IncreaseMoneyRequestService implements IncreaseMoneyRequestUseCase, CreateMemberMoneyUseCase {
    private final CountDownLatchManager countDownLatchManager;
    private final SendRechargingMoneyTaskPort sendRechargingMoneyTaskPort;
    private final GetMembershipPort membershipPort;
    private final IncreaseMoneyPort increaseMoneyPort;
    private final MoneyChangingRequestMapper mapper;
    private final MemberMoneyMapper memberMoneyMapper;
    private final CommandGateway commandGateway;
    private final CreateMemberMoneyPort createMemberMoneyPort;
    private final GetMemberMoneyPort getMemberMoneyPort;
    private final GetMemberMoneyListPort getMemberMoneyListPort;

    @Override
    public MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command) {

        //머니의 충전, 증액이라는 과정
        //1. 고객 정보가 정상인지 확인 (멤버)
        membershipPort.getMembership(command.getTargetMembershipId());
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

    @Override
    public MoneyChangingRequest increaseMoneyRequestAsync(IncreaseMoneyRequestCommand command) {

        //SubTask
        //각 서비스에 특정 membershipId & Validation를 하기위한 Task
        // 1. Subtask, Task
        SubTask validMemberTask = SubTask.builder()
                .subTaskName("validMemberTask: "+"멤버쉽 유효성 검사")
                .membershipID(command.getTargetMembershipId())
                .taskType("membership")
                .status("ready")
                .build();

        // Bankibg Sub Task
        // Banking Account 유효성검사
        SubTask validBankingAccountTask = SubTask.builder()
                .subTaskName("validBankingAccountTask: "+"뱅킹 계좌 유효성 검사")
                .membershipID(command.getTargetMembershipId())
                .taskType("banking")
                .status("ready")
                .build();
        // 요청된 금액을 Firmbanking 요청 -> 무조건 ok라고 가정..


        List<SubTask> subTaskList=new ArrayList<>();
        subTaskList.add(validMemberTask);
        subTaskList.add(validBankingAccountTask);

        RechargingMoneyTask task = RechargingMoneyTask.builder()
                .taskID(UUID.randomUUID().toString())
                .taskName("Increase Money Task / 머니 충전 Task")
                .subTaskList(subTaskList)
                .moneyAmount(command.getAmount())
                .membershipID(command.getTargetMembershipId())
                .toBankName("mybank").build();

        // 2. Kafka Cluster Produce
        //Task Produce
        sendRechargingMoneyTaskPort.sendRechargingMoneyTaskPort(task);
        countDownLatchManager.addCountDownLatch(task.getTaskID());

        // 3. Wait
        try {
            //taskComsumer에서  아래 키를 가진 랜치를 받아와서 CountDown을 하게 되면 멈춰있다가 통과됨
            countDownLatchManager.getCountDownLatch(task.getTaskID()).await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 3-1. task-consumer
        // 등록된 sub-task, status 모두 ok -> task 결과를 Produce

        // 4. Task Result Consume
        //받은 응답을 다시, countDownLatchManager 를 통해서 결과 데이터를 받아야한다
        String result= countDownLatchManager.getDataForKey(task.getTaskID());
        if(result.equals("success")){
            //4-1. Consume ok, Logic
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
        }else{
            //4-2. Consume fail, Logic
            return null;

        }

        // 5. Consume ok, Logic

        return null;
    }

    @Override
    public void createMemberMoney(CreateMemberMoneyCommand command) {
        MemberMoneyCreatedCommand axonCommand = new MemberMoneyCreatedCommand(command.getMembershipId());
        //command Gateway에 보냄
        commandGateway.send(axonCommand).whenComplete((result,throwable)->{
            if(throwable!=null){
                //문제 발생
                System.out.println("throwable = "+throwable.getMessage());
                throw new RuntimeException(throwable);
            }else{
                System.out.println("CreateMemberMoney result = "+result);
                createMemberMoneyPort.createMemberMoney(
                        new MemberMoney.MembershipId(command.getMembershipId()),
                        new MemberMoney.MoneyAggregateIdentifier(result.toString())
                );
            }
        });
    }

    @Override
    public List<MemberMoney> findMemberMoneyListByMembershipIds(FindMemberMoneyListByMembershipIdsCommand command) {
        // 여러개의 membership Ids 를 기준으로, memberMoney 정보를 가져와야 해요.
        List<MemberMoneyJpaEntity> memberMoneyJpaEntityList = getMemberMoneyListPort.getMemberMoneyPort(command.getMembershipIds());
        List<MemberMoney> memberMoneyList = new ArrayList<>();

        for(MemberMoneyJpaEntity memberMoneyJpaEntity : memberMoneyJpaEntityList) {
            memberMoneyList.add(memberMoneyMapper.mapToDomainEntity(memberMoneyJpaEntity));
        }

        return memberMoneyList;
    }

    @Override
    public void increaseMoneyRequestByEvent(IncreaseMoneyRequestCommand command) {
        MemberMoneyJpaEntity memberMoneyJpaEntity = getMemberMoneyPort.getMemberMoney(new MemberMoney.MembershipId(command.getTargetMembershipId()));

        // 🚨 Debug 로그 추가
        System.out.println("Fetched MemberMoneyJpaEntity: " + memberMoneyJpaEntity);
        String memberMoneyAggregateIdentifier = memberMoneyJpaEntity.getAggregateIdentifier();
        // 🚨 Debug 로그 추가
        System.out.println("memberMoneyAggregateIdentifier: " + memberMoneyAggregateIdentifier);

        // Saga 의 시작을 나타내는 커맨드!
        // RechargingMoneyRequestCreateCommand
        commandGateway.send(new RechargingMoneyRequestCreateCommand(memberMoneyAggregateIdentifier,
                UUID.randomUUID().toString(),
                command.getTargetMembershipId(),
                command.getAmount())
        ).whenComplete(
                (result, throwable) -> {
                    if (throwable != null) {
                        throwable.printStackTrace();
                        throw new RuntimeException(throwable);
                    } else {
                        System.out.println("IncreaseMoney result = " + result); // aggregateIdentifier
                    }
                }
        );



//        String aggregateIdentifier = memberMoneyJpaEntity.getAggregateIdentifier();
//        // command
//        commandGateway.send(IncreaseMemberMoneyCommand.builder()
//                        .aggregateIdentifier(aggregateIdentifier)
//                        .membershipId(command.getTargetMembershipId())
//                        .amount(command.getAmount()).build())
//        .whenComplete(
//                (result, throwable) -> {
//                    if (throwable != null) {
//                        throwable.printStackTrace();
//                        throw new RuntimeException(throwable);
//                    } else {
//                        // Increase money -> money 증가
//                        System.out.println("increaseMoney result = " + result);
//                        increaseMoneyPort.increaseMoney(
//                                new MemberMoney.MembershipId(command.getTargetMembershipId())
//                                , command.getAmount());
//                    }
//                }
//        );
    }



}
