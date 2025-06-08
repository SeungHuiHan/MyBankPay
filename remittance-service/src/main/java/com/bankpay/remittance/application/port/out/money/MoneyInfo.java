package com.bankpay.remittance.application.port.out.money;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

//  송금서비스에서 필요한 머니의 정보
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyInfo {
    private String membershipId;
    private int balance;
}
