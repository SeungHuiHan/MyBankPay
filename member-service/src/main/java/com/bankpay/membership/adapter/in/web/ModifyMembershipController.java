package com.bankpay.membership.adapter.in.web;

import com.bankpay.common.WebAdapter;
import com.bankpay.membership.application.port.in.ModifyMembershipCommand;
import com.bankpay.membership.application.port.in.ModifyMembershipUseCase;
import com.bankpay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class ModifyMembershipController {
    private final ModifyMembershipUseCase modifyMembershipUseCase;
    @PostMapping("/membership/{membershipId}")
    ResponseEntity<Membership> modifyMembershipByMemberId(@RequestBody ModifyMembershipRequest request) {

        //request ~~

        //request -> Command (추상화 계층)

        //useCase ~~ (request)

        ModifyMembershipCommand command = ModifyMembershipCommand.builder()
                .membershipId(request.getMembershipId())
                .name(request.getName())
                .address(request.getAddress())
                .email(request.getEmail())
                .isValid(request.isValid())
                .isCorp(request.isCorp())
                .build();

        return ResponseEntity.ok(modifyMembershipUseCase.modifyMembership(command));

    }
}
