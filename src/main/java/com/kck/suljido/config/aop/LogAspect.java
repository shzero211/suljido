package com.kck.suljido.config.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
public class LogAspect {
    @Around("execution(* com.suljido.controller..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start =System.currentTimeMillis();

        HttpServletRequest request= ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        MDC.put("httpMethod",request.getMethod());
        MDC.put("requestUri",request.getRequestURI());
        try {
            return joinPoint.proceed();
        }finally {
            long executionTime=System.currentTimeMillis() - start;

            MDC.put("elapsedTime",String.valueOf(executionTime));

            // [중요] 처리 시간이 2초 이상이면 WARN, 아니면 INFO
            if (executionTime > 2000) {
                log.warn("[SLOW_API] {} executed in {} ms", request.getRequestURI(), executionTime);
            } else {
                log.info("[API_END] {} executed in {} ms", request.getRequestURI(), executionTime);
            }
        }


    }
}
