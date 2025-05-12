package com.naivez.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspectForServices {

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void isServiceLayer() {
    }

    @Before("isServiceLayer() && target(service)")
    public void addInputParametersLogging(JoinPoint joinPoint, Object service) {
        String methodName = joinPoint.getSignature().getName();
        String serviceClassName = service.getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();

        log.info("Entering method '{}()' in service '{}' with arguments: {}",
                methodName, serviceClassName, args.length > 0 ? Arrays.toString(args) : "No arguments");
    }

    @AfterReturning(value = "isServiceLayer() && target(service)", returning = "result", argNames = "joinPoint,result,service")
    public void addLoggingAfterReturning(JoinPoint joinPoint, Object result, Object service) {
        String methodName = joinPoint.getSignature().getName();
        String serviceClassName = service.getClass().getSimpleName();

        if (result == null) {
            log.info("Method '{}' in service '{}' executed successfully and returned nothing", methodName, serviceClassName);
        } else {
            log.info("Method '{}' in service '{}' executed successfully and returned: {}", methodName, serviceClassName, result);
        }
    }

    @AfterThrowing(value = "isServiceLayer() && target(service)", throwing = "ex")
    public void addLoggingAfterThrowing(JoinPoint joinPoint, Throwable ex, Object service) {
        String methodName = joinPoint.getSignature().getName();
        String serviceClassName = service.getClass().getSimpleName();
        String errorMessage = ex.getMessage();

        log.error("Method '{}' in service '{}' threw an exception: {}. Error message: {}",
                methodName, serviceClassName, ex.getClass().getSimpleName(), errorMessage);
    }
}
