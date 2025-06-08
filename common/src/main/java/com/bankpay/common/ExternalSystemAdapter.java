package com.bankpay.common;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
//영속성 계층의 어댑터를 의미하는 어노테이션
public @interface ExternalSystemAdapter {
}
