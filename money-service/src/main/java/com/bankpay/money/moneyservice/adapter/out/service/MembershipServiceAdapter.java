package com.bankpay.money.moneyservice.adapter.out.service;

import com.bankpay.common.CommonHttpClient;
import com.bankpay.money.moneyservice.application.port.out.GetMembershipPort;
import com.bankpay.money.moneyservice.application.port.out.MembershipStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MembershipServiceAdapter implements GetMembershipPort {

    private final CommonHttpClient commonHttpClient;

    private final String membershipServiceUrl;

    public MembershipServiceAdapter(CommonHttpClient commonHttpClient,@Value("${service.membership.url}") String membershipServiceUrl) {
        this.commonHttpClient = commonHttpClient;
        this.membershipServiceUrl = membershipServiceUrl;
    }

    @Override
    public MembershipStatus getMembership(String membershipId) {

        String url =String.join("/", membershipServiceUrl,"membership", membershipId);
        try {
           String jsonResponse= commonHttpClient.sendGetRequest(url).body();
           //json Membership

            ObjectMapper objectMapper = new ObjectMapper();
            Membership membership = objectMapper.readValue(jsonResponse, Membership.class); //JSON 형태로 파싱
            if(membership.isValid()){
                return new MembershipStatus(membership.getMembershipId(),true);
            }else{
                return new MembershipStatus(membership.getMembershipId(),false);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //실제로 http Call
        //http Client구현
    }
}
