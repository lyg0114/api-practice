package com.apipractice.global.aop.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
@Aspect
public class TraceAspect {

  @Around("@annotation(com.apipractice.global.aop.annotation.Trace)")
  public Object doTrace(ProceedingJoinPoint joinPoint) throws Throwable {

    log.info("start - {}", joinPoint.getSignature());
    Object proceed = joinPoint.proceed();
    log.info("end - {}", joinPoint.getSignature());

    return proceed;
  }
}
