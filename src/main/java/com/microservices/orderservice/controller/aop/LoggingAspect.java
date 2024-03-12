package com.microservices.orderservice.controller.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Locale;

/**
 * Aspect for logging method executions in the ProductService controllers.
 *
 * @author priyanshu
 * @version 1.0
 * @since 23/02/2024
 */
@Aspect
@RequiredArgsConstructor
@Slf4j
@Component
public class LoggingAspect {

    private final MessageSource messageSource;

    /**
     * Pointcut definition for all methods in the OrderService controllers.
     */
    @Pointcut("execution(* com.microservices.orderservice.controller.impl.*.*(..))")
    public void controller() {
    }

    /**
     * Advice to log method entry and input arguments before execution.
     *
     * @param joinPoint The JoinPoint representing the method execution.
     */
    @Before("controller()")
    public void logRequestData(JoinPoint joinPoint) {
        log.debug("Entering in {} : {}()", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        if (log.isDebugEnabled()) {
            log.debug("Method arguments : {}", Arrays.toString(joinPoint.getArgs()));
        }
    }

    /**
     * Advice to log method exit and return value after successful execution.
     *
     * @param joinPoint The JoinPoint representing the method execution.
     * @param result    The return value of the method.
     */
    @AfterReturning(value = "controller()", returning = "result")
    public void logResponseData(JoinPoint joinPoint, Object result) {
        log.debug("Method return value : {}", result);
        log.debug("Exiting from {} : {}()",
                  joinPoint.getSignature().getDeclaringTypeName(),
                  joinPoint.getSignature().getName());
    }

    /**
     * Advice to log method exceptions and error details if an exception occurs during execution.
     *
     * @param joinPoint The JoinPoint representing the method execution.
     * @param e         The Throwable representing the exception.
     */
    @AfterThrowing(value = "controller()", throwing = "e")
    public void logMethodCrash(JoinPoint joinPoint, Throwable e) {
        log.error("Exception occurred inside {} : {}() because : {}",
                  joinPoint.getSignature().getDeclaringTypeName(),
                  joinPoint.getSignature().getName(),
                  e.getMessage() != null ?
                          messageSource.getMessage(e.getMessage(), null, Locale.getDefault()) :
                          "NULL");
    }

    /**
     * Advice to log method execution time.
     *
     * @param pjp The ProceedingJoinPoint representing the method execution.
     * @return The return value of the method.
     * @throws Throwable If an exception occurs during method execution.
     */
    @Around(value = "controller()")
    public Object trackTime(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object obj = pjp.proceed();
        long endTime = System.currentTimeMillis();
        log.debug("{} {} took {} ms to execute",
                  pjp.getSignature().getDeclaringTypeName(),
                  pjp.getSignature().getName(),
                  (endTime - startTime));
        return obj;
    }
}
