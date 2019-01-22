package com.deeps.easycable.api.aop;

import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@Component
@Log4j2
public class ApiLoggingAspect {

	@Around("@annotation(LogMethodExecutionTime)")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis();

		Object proceed = joinPoint.proceed();

		long executionTime = System.currentTimeMillis() - start;

		log.info(joinPoint.getSignature() + " executed in " + executionTime / 1000.0 + " Second");		
		return proceed;
	}
}
