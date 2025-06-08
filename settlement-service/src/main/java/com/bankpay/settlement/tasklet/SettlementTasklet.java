package com.bankpay.settlement.tasklet;

import com.bankpay.settlement.adapter.out.service.Payment;
import com.bankpay.settlement.port.out.GetRegisteredBankAccountPort;
import com.bankpay.settlement.port.out.PaymentPort;
import com.bankpay.settlement.port.out.RegisteredBankAccountAggregateIdentifier;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SettlementTasklet implements Tasklet {
    private final GetRegisteredBankAccountPort getRegisteredBankAccountPort;
    private final PaymentPort paymentPort;
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext){

        System.out.println("Tasklet execute!: "
                        +chunkContext.getStepContext().getStepExecution().getJobParameters().getLong("time"));
        // 1. payment service 에서 결제 완료된 결제 내역을 조회한다.
        List<Payment> normalStatusPaymentList = paymentPort.getNormalStatusPayments();
        System.out.println("normalStatusPaymentList: " + normalStatusPaymentList);
//        paymentPort.finishSettlement(29L);
        // 2. 각 결제 내역의 franchiseId 에 해당하는 멤버십 정보(membershipId)에 대한
        // 뱅킹 정보(계좌번호) 를 가져와서
        Map<String, FirmbankingRequestInfo> franchiseIdToBankAccountMap = new HashMap<>();
        for (Payment payment : normalStatusPaymentList) {
            RegisteredBankAccountAggregateIdentifier entity = getRegisteredBankAccountPort.getRegisteredBankAccount(payment.getFranchiseId());
            franchiseIdToBankAccountMap.put(payment.getFranchiseId()
                    , new FirmbankingRequestInfo(entity.getBankName(), entity.getBankAccountNumber()));
        }

        System.out.println("2 완료.");
        // 3. 각 franchiseId 별로, 정산 금액을 계산해주고
        // 수수료를 제하지 않았어요.
        for (Payment payment : normalStatusPaymentList) {
            FirmbankingRequestInfo firmbankingRequestInfo = franchiseIdToBankAccountMap.get(payment.getFranchiseId());
            double fee = Double.parseDouble(payment.getFranchiseFeeRate());
            int caculatedPrice = (int) ((100 - fee) * payment.getRequestPrice() * 100);
            firmbankingRequestInfo.setMoneyAmount(firmbankingRequestInfo.getMoneyAmount() + caculatedPrice);
        }

        System.out.println("3 완료.");

        // 4. 계산된 금액을 펌뱅킹 요청해주고
        for (FirmbankingRequestInfo firmbankingRequestInfo : franchiseIdToBankAccountMap.values()) {
            System.out.println("firmbankingRequestInfo: "+firmbankingRequestInfo.getBankName()+" "+firmbankingRequestInfo.getBankAccountNumber()+" "+firmbankingRequestInfo.getMoneyAmount());
            getRegisteredBankAccountPort.requestFirmbanking(
                    firmbankingRequestInfo.getBankName()
                    , firmbankingRequestInfo.getBankAccountNumber()
                    , firmbankingRequestInfo.getMoneyAmount());
        }
        System.out.println("4 완료.");

        // 5. 정산 완료된 결제 내역은 정산 완료 상태로 변경해준다.
        for (Payment payment : normalStatusPaymentList) {
            paymentPort.finishSettlement(payment.getPaymentId());
        }
        System.out.println("5 완료.");

        return RepeatStatus.FINISHED;
    }
}