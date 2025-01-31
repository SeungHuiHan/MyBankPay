package com.bankpay.banking.adapter.in.web;

import com.bankpay.banking.application.port.in.RegisterBankAccountCommand;
import com.bankpay.banking.application.port.in.RegisterBankAccountUseCase;
import com.bankpay.banking.domain.RegisteredBankAccount;
import com.bankpay.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RegisterBankAccountController {

    private final RegisterBankAccountUseCase registerBankAccountUseCase;

    @PostMapping("/banking/account/register")
    public RegisteredBankAccount registerRegisteredBankAccount(@RequestBody RegisterBankAccountRequest request) {

        //request ~~

        //request -> Command (추상화 계층)

        //useCase ~~ (request)

        RegisterBankAccountCommand command = RegisterBankAccountCommand.builder()
                .membershipId(request.getMembershipId())
                .bankName(request.getBankName())
                .bankAccountNumber(request.getBankAccountNumber())
                .linkedStatusIsValid(request.isLinkedStatusIsValid())
                .build();
//        RegisteredBankAccount registeredBankAccount=registerBankAccountUseCase.registerBankAccount(command);
//        if(registeredBankAccount==null){
//            //TODO: Error Handling
//            //throw new RuntimeException("등록 실패");
//            return null;
//        }

        return registerBankAccountUseCase.registerBankAccount(command);

    }
}