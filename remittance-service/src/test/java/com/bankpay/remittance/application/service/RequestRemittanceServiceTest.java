package com.bankpay.remittance.application.service;

import com.bankpay.remittance.adapter.out.persistence.RemittanceRequestJpaEntity;
import com.bankpay.remittance.adapter.out.persistence.RemittanceRequestMapper;
import com.bankpay.remittance.application.port.in.RequestRemittanceCommand;
import com.bankpay.remittance.application.port.out.RequestRemittancePort;
import com.bankpay.remittance.application.port.out.banking.BankingPort;
import com.bankpay.remittance.application.port.out.membership.MembershipPort;
import com.bankpay.remittance.application.port.out.membership.MembershipStatus;
import com.bankpay.remittance.application.port.out.money.MoneyInfo;
import com.bankpay.remittance.application.port.out.money.MoneyPort;
import com.bankpay.remittance.domain.RemittanceRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class RequestRemittanceServiceTest {

    //Inject Mocks
    //@InjectMocks

    @InjectMocks
    private RequestRemittanceService requestRemittanceService;
    @Mock
    private RequestRemittancePort requestRemittancePort;
    @Mock
    private RemittanceRequestMapper mapper;
    @Mock
    private BankingPort bankingPort;
    @Mock
    private MembershipPort membershipPort;
    @Mock
    private MoneyPort moneyPort;

    @BeforeEach
    public void setUp() {
        //이 라인이 InjectMocks 가 포함된 클래스
        //@Mock 들을 주입시켜줌
        MockitoAnnotations.openMocks(this);

        /**
         * @NOTE
         * private final 필드의 경우, setter 를 통해 주입할 수 없기 때문에
         * Reflection or Constructor 를 통해 주입해야 한다.
         */
        requestRemittanceService = new RequestRemittanceService(requestRemittancePort, mapper, membershipPort, moneyPort, bankingPort);
    }

    private static Stream<RequestRemittanceCommand> provideRequestRemittanceCommand() {
        return Stream.of(
                RequestRemittanceCommand.builder()
                        .fromMembershipId("5")
                        .toMembershipId("4")
                        .toBankName("bank22")
                        .remittanceType(0)
                        .toBankAccountNumber("1234")
                        .amount(155500)
                        .build()
        );
    }
//
//    @ParameterizedTest
//    @MethodSource("provideRequestRemittanceCommand") //파라미터로 하는 소스를 활용해서 아래 테스트를 진행하겠다
//    public void testTestMethod(RequestRemittanceCommand testCommand){
//
//        RemittanceRequestJpaEntity tt = new RemittanceRequestJpaEntity(
//                1L,
//                "test_from_membership_id",
//                "test_to_membership_id",
//                "test_to_bank_name",
//                "123-456-789",
//                0,
//                10000,
//                "success"
//        );
//
//        if (testCommand.getFromMembershipId().equals("2")) {
//            when(requestRemittancePort.createRemittanceRequestHistory(testCommand))
//                    .thenReturn(null);
//        } else {
//            when(requestRemittancePort.createRemittanceRequestHistory(testCommand))
//                    .thenReturn(tt);
//            when(mapper.mapToDomainEntity(tt)).thenReturn(RemittanceRequest.generateRemittanceRequest(
//                    new RemittanceRequest.RemittanceRequestId("test_id"),
//                    new RemittanceRequest.RemittanceFromMembershipId("test_from_membership_id"),
//                    new RemittanceRequest.ToBankName("test_to_membership_id"),
//                    new RemittanceRequest.ToBankAccountNumber("123-456-789"),
//                    new RemittanceRequest.RemittanceType(0),
//                    new RemittanceRequest.Amount(10000),
//                    new RemittanceRequest.RemittanceStatus("success")));
//        }
//
//
//        RemittanceRequest want = RemittanceRequest.generateRemittanceRequest(
//                new RemittanceRequest.RemittanceRequestId("test_id"),
//                new RemittanceRequest.RemittanceFromMembershipId("test_from_membership_id"),
//                new RemittanceRequest.ToBankName("test_to_membership_id"),
//                new RemittanceRequest.ToBankAccountNumber("123-456-789"),
//                new RemittanceRequest.RemittanceType(0),
//                new RemittanceRequest.Amount(10000),
//                new RemittanceRequest.RemittanceStatus("success")
//        );
//
//        RemittanceRequest got = requestRemittanceService.testMethod(testCommand);
//        verify(requestRemittancePort, times(1)).createRemittanceRequestHistory(testCommand);
//        verify(mapper, times(1)).mapToDomainEntity(tt);
//        assertEquals(want, got);
//    }

    // 송금 요청을 한 고객이 유효하지 않은 경우 Test
    @ParameterizedTest
    @MethodSource("provideRequestRemittanceCommand")
    public void testRequestRemittanceWhenMembershipInvalid(RequestRemittanceCommand testCommand){


        // 1. Set want 먼저, 어떤 결과가 나올지에 대해서 정의를 한다.
        // want = null

        // 2.  Mocking을 위한 dummy data가 있다면 그 data는 먼저 만들어 준다

        // 3. Setting Dummy Data if needed 그 결과를 위해, Mocking 해준다.
        when(requestRemittancePort.createRemittanceRequestHistory(testCommand))
                .thenReturn(null);
        when(membershipPort.getMembershipStatus(testCommand.getFromMembershipId()))
                .thenReturn(new MembershipStatus(testCommand.getFromMembershipId(), false));


        //4. 그리고 그 Mocking된 mock들을 사용해서, 테스트를 진행한다.
        RemittanceRequest got = requestRemittanceService.requestRemittance(testCommand);

        //5. Verify를 통해서, 테스트가 잘 진행되었는지 확인한다.
        verify(requestRemittancePort, times(1)).createRemittanceRequestHistory(testCommand);
        verify(membershipPort, times(1)).getMembershipStatus(testCommand.getFromMembershipId());

        //6. Assert를 통해, 최종적으로 이 테스트가 잘 진행되었는지 확인한다.
        assertEquals(null, got);
    }

    //잔액이 충분하지 않은 경우  Test
    @ParameterizedTest
    @MethodSource("provideRequestRemittanceCommand")
    public void testRequestRemittanceWhenNotEnoughMoney(RequestRemittanceCommand testCommand){
        // Membership 상태가 invalid 인 경우 Test
        // 1. Set want 먼저, 어떤 결과가 나올지에 대해서 정의를 한다.
        // want = null

        // 2.  Mocking을 위한 dummy data가 있다면 그 data는 먼저 만들어 준다

        MoneyInfo dummyMoneyInfo = new MoneyInfo(testCommand.getFromMembershipId(), 1000) ;

        // 3. Setting Dummy Data if needed 그 결과를 위해, Mocking 해준다.
        when(requestRemittancePort.createRemittanceRequestHistory(testCommand))
                .thenReturn(null);
        when(membershipPort.getMembershipStatus(testCommand.getFromMembershipId()))
                .thenReturn(new MembershipStatus(testCommand.getFromMembershipId(), true));
        when(moneyPort.getMoneyInfo(testCommand.getFromMembershipId()))
                .thenReturn(new MoneyInfo(testCommand.getFromMembershipId(), 1000));

        int rechargeAmount = (int) Math.ceil((testCommand.getAmount() - dummyMoneyInfo.getBalance()) / 10000.0) * 10000;
        when(moneyPort.requestMoneyRecharging(testCommand.getFromMembershipId(), rechargeAmount))
                .thenReturn(false);

        //4. 그리고 그 Mocking된 mock들을 사용해서, 테스트를 진행한다.
        RemittanceRequest got = requestRemittanceService.requestRemittance(testCommand);

        //5. Verify를 통해서, 테스트가 잘 진행되었는지 확인한다.
        verify(requestRemittancePort, times(1)).createRemittanceRequestHistory(testCommand);
        verify(membershipPort, times(1)).getMembershipStatus(testCommand.getFromMembershipId());
        verify(moneyPort, times(1)).getMoneyInfo(testCommand.getFromMembershipId());
        verify(moneyPort, times(1)).requestMoneyRecharging(testCommand.getFromMembershipId(), rechargeAmount);

        //6. Assert를 통해, 최종적으로 이 테스트가 잘 진행되었는지 확인한다.
        assertEquals(null, got);
    }

}