package com.bankpay.membership.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="membership")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembershipJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long membershipId;

    private String name;

    private String email;

    private String address;

    private boolean isValid;

    private boolean isCorp;

    @Override
    public String toString() {
        return "MembershipJpaEntity{" +
                "membershipId=" + membershipId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", isValid=" + isValid +
                ", isCorp=" + isCorp +
                '}';
    }

    public MembershipJpaEntity(String name, String email, String address, boolean isValid, boolean isCorp) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.isValid = isValid;
        this.isCorp = isCorp;
    }
}

