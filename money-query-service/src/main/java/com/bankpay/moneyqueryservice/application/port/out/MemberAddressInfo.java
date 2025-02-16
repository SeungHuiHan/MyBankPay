package com.bankpay.moneyqueryservice.application.port.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberAddressInfo {
    String membershipId;
    String address;
}