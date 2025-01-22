package com.bankpay.banking.adapter.out.external.bank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//외부 은행망에 실제로 요청할 정보
//외부의 은행과 통신을 담당
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExternalFirmbankingRequest {

    private String fromBankName;

    private String fromBankAccountNumber;

    private String toBankName;

    private String toBankAccountNumber;

}


