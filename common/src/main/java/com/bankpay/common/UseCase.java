package com.bankpay.common;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
//논리적으로만 정의 어노테이션
public @interface UseCase {
}
