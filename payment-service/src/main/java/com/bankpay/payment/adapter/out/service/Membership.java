package com.bankpay.payment.adapter.out.service;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//banking-service만을 위함
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Membership {

    private String membershipId;

    private String name;

    private String email;

    private String address;

    private boolean isValid;

    private boolean isCorp;
}
