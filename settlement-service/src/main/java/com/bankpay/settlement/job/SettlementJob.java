package com.bankpay.settlement.job;

import com.bankpay.settlement.tasklet.SettlementTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
@RequiredArgsConstructor
public class SettlementJob {
    private final SettlementTasklet settlementTasklet;

    @Bean
    public Job settlement(JobRepository jobRepository, Step settlementStep) {
        return new org.springframework.batch.core.job.builder.JobBuilder("settlement", jobRepository)
                .start(settlementStep)
                .build();
    }

    @Bean
    public Step settlementStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("settlementStep", jobRepository)
                .tasklet(settlementTasklet, transactionManager)
                .build();
    }

}