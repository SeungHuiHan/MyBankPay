package com.bankpay.banking.adapter.out.external.bank;

import com.bankpay.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import com.bankpay.banking.adapter.out.persistence.SpringDataRegisteredBankAccountRepository;
import com.bankpay.banking.application.port.out.RegisterBankAccountPort;
import com.bankpay.banking.application.port.out.RequestBankAccountInfoPort;
import com.bankpay.banking.application.port.out.RequestExternalFirmbankingPort;
import com.bankpay.banking.domain.FirmbankingRequest;
import com.bankpay.banking.domain.RegisteredBankAccount;
import com.bankpay.common.ExternalSystemAdapter;
import com.bankpay.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class BankAccountAdapter implements RequestBankAccountInfoPort, RequestExternalFirmbankingPort {

    private final SpringDataRegisteredBankAccountRepository registeredBankAccountRepository;

    @Override
    public BankAccount getBankAccountInfo(GetBankAccountRequest request) {

        //실제로 외부 은행에 http을 통해서
        //실제 은행 계좌 정보를 가져오고

        //실제 은행 계좌 -> BankAccount
        return new BankAccount(request.getBankName(),request.getBankAccountNumber(),true);

    }


    @Override
    public FirmbankingResult requestExternalFirmbanking(ExternalFirmbankingRequest request) {
        //시렞로 외부 은행에 http 통신을 통해서
        //펌뱅킹 요청을 하고

        //그 결과를
        //외부 은행의 실제 결과를 ->  my bank pay의 FirmbankingResult로 받는다
        FirmbankingResult result=new FirmbankingResult(0); //결과를 true로 가정
        return result;
    }
}
