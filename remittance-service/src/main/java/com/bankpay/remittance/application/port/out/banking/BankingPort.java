package com.bankpay.remittance.application.port.out.banking;

public interface BankingPort {
    BankingInfo getMembershipBankingInfo(String bankName, String bankAccountNumber); //특정 뱅킹의 정보를 가져옴

    boolean requestFirmbanking(String bankName, String bankAccountNumber, int amount); //법인계좌에서  외부 은행 계좌로
}
