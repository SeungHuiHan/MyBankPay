package com.bankpay.membership.adapter.in.web;

import com.bankpay.common.WebAdapter;
import com.bankpay.membership.application.port.in.FindMembershipCommand;
import com.bankpay.membership.application.port.in.FindMembershipListByAddressCommand;
import com.bankpay.membership.application.port.in.FindMembershipUseCase;
import com.bankpay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@WebAdapter
@RestController
@RequiredArgsConstructor
public class FindMembershipController {

    private final FindMembershipUseCase findMembershipUseCase;
    @GetMapping("/membership/{membershipId}")
    ResponseEntity<Membership> findMembershipByMemberId(@PathVariable String membershipId){

        //request ~~

        //request -> Command (추상화 계층)

        //useCase ~~ (request)

        FindMembershipCommand command = FindMembershipCommand.builder()
                .membershipId(membershipId)
                .build();

        return ResponseEntity.ok(findMembershipUseCase.findMembership(command));

    }

    @GetMapping(path = "/membership/address/{addressName}")
    ResponseEntity<List<Membership>> findMembershipListByAddress(@PathVariable String addressName) {
        FindMembershipListByAddressCommand command = FindMembershipListByAddressCommand.builder()
                .addressName(addressName)
                .build();
        return ResponseEntity.ok(findMembershipUseCase.findMembershipListByAddress(command));
    }
}
