package com.bankpay.banking.adapter.in.web;

import com.bankpay.banking.application.port.in.FindBankAccountCommand;
import com.bankpay.banking.application.port.in.FindBankAccountUseCase;
import com.bankpay.banking.domain.RegisteredBankAccount;
import com.bankpay.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@WebAdapter
@RestController
@RequiredArgsConstructor
public class FindRegisteredBankAccountController {

    private final FindBankAccountUseCase findBankAccountUseCase;
    @GetMapping("/banking/account/{registeredBankAccountId}")
    ResponseEntity<RegisteredBankAccount> findRegisteredBankAccountByAccountId(@PathVariable String registeredBankAccountId){

        //request ~~

        //request -> Command (추상화 계층)

        //useCase ~~ (request)

        FindBankAccountCommand command = FindBankAccountCommand.builder()
                .registeredBankAccountId(registeredBankAccountId)
                .build();

        return ResponseEntity.ok(findBankAccountUseCase.findRegisteredBankAccount(command));

    }
}
