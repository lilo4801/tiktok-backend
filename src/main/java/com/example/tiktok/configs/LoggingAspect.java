package com.example.tiktok.configs;

import com.example.tiktok.controllers.AuthController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.example.tiktok.controllers.*.*(..))")
    public void beforeControllerMethod(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Logger LOGGER = LogManager.getLogger(className);
        LOGGER.info("Before executing " + className + " controller method: " + methodName);
    }

    @After("execution(* com.example.tiktok.controllers.*.*(..))")
    public void afterControllerMethod(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Logger LOGGER = LogManager.getLogger(className);
        LOGGER.info("After executing " + className + " controller method: " + methodName);

    }

    @Before("execution(* com.example.tiktok.services.*.*(..))")
    public void beforeServiceMethod(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Logger LOGGER = LogManager.getLogger(className);
        LOGGER.info("Before executing " + className + " service method: " + methodName);
    }

    @After("execution(* com.example.tiktok.services.*.*(..))")
    public void afterServiceMethod(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Logger LOGGER = LogManager.getLogger(className);
        LOGGER.info("After executing " + className + " service method: " + methodName);

    }
}


