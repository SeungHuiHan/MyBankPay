package com.bankpay.remittance.adapter.in.web;

import com.bankpay.common.WebAdapter;
import com.bankpay.remittance.application.port.in.FindRemittanceCommand;
import com.bankpay.remittance.application.port.in.FindRemittanceUseCase;
import com.bankpay.remittance.application.port.in.RequestRemittanceCommand;
import com.bankpay.remittance.application.port.in.RequestRemittanceUseCase;
import com.bankpay.remittance.domain.RemittanceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class FindRemittanceHistoryController {

    private final FindRemittanceUseCase findRemittanceUseCase;
    @GetMapping(path = "/remittance/{membershipId}")
    List<RemittanceRequest> findRemittanceHistory(@PathVariable String membershipId) {
        FindRemittanceCommand command = FindRemittanceCommand.builder()
                .membershipId(membershipId)
                .build();

        return findRemittanceUseCase.findRemittanceHistory(command);
    }
}
