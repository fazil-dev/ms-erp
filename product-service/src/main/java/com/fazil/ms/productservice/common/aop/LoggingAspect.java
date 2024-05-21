package com.fazil.ms.productservice.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Slf4j
@Profile("dev")
public class LoggingAspect {

    @Pointcut("execution(public * com.fazil.ms.productservice.controller.*.*(..))")
    private void controllerMethodsLogging() {
    }

    @Before("controllerMethodsLogging()")
    private void logBeforeControllerMethods(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.info("AOP:before method {} called with arguments {}. ", methodName, Arrays.toString(args));
    }

    @After("controllerMethodsLogging()")
    private void logAfterControllerMethods(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.info("AOP:after method {} called.", methodName);
    }
}
