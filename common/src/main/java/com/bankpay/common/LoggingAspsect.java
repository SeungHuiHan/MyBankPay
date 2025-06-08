package com.bankpay.common;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspsect {
    private final LoggingProducer loggingProducer;

    //이 경로에 있는 모든 메서드가 실행되기 이전에 이걸 먼저 실행해라
    @Before("execution(* com.bankpay.*.adapter.in.web.*.*(..))")
    public void beforeMethodExecution(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        loggingProducer.sendMessage("logging", "Before executing ,ethod: " + methodName);
        //Produce Access log
    }
}
