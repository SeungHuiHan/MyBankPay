package com.bankpay.membership.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE) //접근 제어자를 private하게 가져간다
public class Membership {

    private final String membershipId;

    private final String name;

    private final String email;

    private final String address;

    private final boolean isValid;

    private final boolean isCorp;

    @Getter private final String refreshToken;
    //Membership
    //오염이 되면 안되는 클래스, 고객 정보, 핵심 도메인

    //이 클래스를 통하지 않고는 Membership이라는 객체를 만들 수 없다
    public static Membership generateMember(MembershipId membershipId
            , MembershipName membershipName
            , MembershipEmail membershipEmail
            , MembershipAddress membershipAddress
            , MembershipIsValid membershipIsValid
            , MembershipIsCorp membershipIsCorp
            , MembershipRefreshToken membershipRefreshToken) {
        return new Membership(membershipId.membershipId
                ,membershipName.membershipName
                ,membershipEmail.membershipEmail
                ,membershipAddress.membershipAddress
                ,membershipIsValid.membershipIsValid
                ,membershipIsCorp.membershipIsCorp
                ,membershipRefreshToken.refreshToken);
    }

    //싱글톤
    @Value
    public static class MembershipId{
        public MembershipId(String value){
            this.membershipId=value;
        }
       String membershipId;
    }

    @Value
    public static class MembershipName{
        public MembershipName(String value){
            this.membershipName=value;
        }
        String membershipName;
    }

    @Value
    public static class MembershipEmail{
        public MembershipEmail(String value){
            this.membershipEmail=value;
        }
        String membershipEmail;
    }

    @Value
    public static class MembershipAddress{
        public MembershipAddress(String value){
            this.membershipAddress=value;
        }
        String membershipAddress;
    }
    @Value
    public static class MembershipIsValid{
        public MembershipIsValid(boolean value){
            this.membershipIsValid=value;
        }
        boolean membershipIsValid;
    }

    @Value
    public static class MembershipIsCorp{
        public MembershipIsCorp(boolean value){
            this.membershipIsCorp=value;
        }
        boolean membershipIsCorp;
    }

    @Value
    public static class MembershipRefreshToken {
        public MembershipRefreshToken(String value) {
            this.refreshToken = value;
        }
        String refreshToken;
    }

}
