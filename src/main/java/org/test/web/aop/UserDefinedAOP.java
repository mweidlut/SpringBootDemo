package org.test.web.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class UserDefinedAOP {

    private Logger logger = LoggerFactory.getLogger(UserDefinedAOP.class);

    @Around("execution(* org.test.web..*.*(..)) && @annotation(org.test.web.annotation.CheckMethod)")
    public Object round(ProceedingJoinPoint joinPoint) {

        logger.info(">>>>> intercept method {}", joinPoint.getSignature().getName());

        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return null;
    }
}
