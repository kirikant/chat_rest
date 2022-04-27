package com.chat.chat.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Aspect
@Component
public class RestLogger {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Pointcut("@annotation(NeedToLog)")
    public  void restLog(){}

    @After("restLog()")
    public void log(JoinPoint joinPoint) throws  Throwable{
        String name = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.info("Call method "+ name + " with args "+Arrays.toString(args));

    }
}
