package com.ujjwal.blogapi.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class PerformanceLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceLoggingAspect.class);

    // Applies to all service methods for logging/transactions
    @Pointcut("execution(* com.ujjwal.blogapi.service.*.*(..))")
    public void serviceMethods() {}

    // Intercepts execution to log details and track performance time
    @Around("serviceMethods()")
    public Object profileServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        Object[] args = joinPoint.getArgs();

        // Log before method execution
        logger.info("[AOP-START] Executing: {}.{}() with arguments: {}", className, methodName, Arrays.toString(args));

        long startTime = System.currentTimeMillis();

        Object result;
        try {
            // Proceed with the actual method execution
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            // Log if something goes wrong, like a missing category or post
            logger.error("[AOP-EXCEPTION] Exception in {}.{}() -> Message: {}", className, methodName, throwable.getMessage());
            throw throwable;
        }

        long executionTime = System.currentTimeMillis() - startTime;

        // Log completion and total execution time
        logger.info("[AOP-END] Completed: {}.{}() in {}ms", className, methodName, executionTime);

        return result;
    }

}
