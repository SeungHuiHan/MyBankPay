package com.bankpay.membership.adapter.in.web;

import com.bankpay.membership.application.port.in.RegisterMembershipCommand;
import com.bankpay.membership.application.port.in.RegisterMembershipUseCase;
import com.bankpay.membership.domain.Membership;
import common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RegisterMembershipController {

    private final RegisterMembershipUseCase registerMembershipUseCase;

    @PostMapping("/membership/register")
    public Membership registerMembership(@RequestBody RegisterMembershipRequest request) {

        //request ~~

        //request -> Command (추상화 계층)

        //useCase ~~ (request)

        RegisterMembershipCommand command = RegisterMembershipCommand.builder()
                .name(request.getName())
                .address(request.getAddress())
                .email(request.getEmail())
                .isValid(true)
                .isCorp(request.isCorp())
                .build();

        return registerMembershipUseCase.registerMembership(command);

    }
}