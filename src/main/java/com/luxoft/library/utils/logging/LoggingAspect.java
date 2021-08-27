package com.luxoft.library.utils.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    // adding logger to info called command from controllers
    private Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.luxoft.library.controllers..*(..))")
    public void pointCut() {
    }

    @After("pointCut()")
    public void afterAdvice(JoinPoint jp) {
        System.out.println();
        logger.info("COMMAND WAS CALLED '" + jp.toShortString() + "'");
    }
}
