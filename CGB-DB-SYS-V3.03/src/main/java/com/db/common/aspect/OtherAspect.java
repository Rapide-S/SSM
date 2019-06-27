package com.db.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

@Aspect
@Service
public class OtherAspect {

	// 提取共性
	@Pointcut("bean(sysMenuServiceImpl)")
	public void doAspect() {
	}

	@Before("doAspect()")
	public void before() {
		System.out.println("@Before");

	}

	@Before("doAspect()")
	public void after() {
		System.out.println("@After");

	}

	@AfterReturning("doAspect()")
	public void doAfterReturning() {
		System.out.println("@AfterReturning");

	}

	@AfterThrowing("doAspect()")
	public void doAfterThrowing(JoinPoint jp) {
		System.out.println("@AfterThrowing");

	}

	@Around("doAspect()")//只有环绕通通知中是ProceedingJoinPoint，其他通知中JoinPoint也可以
	public Object doAround(ProceedingJoinPoint jp) throws Throwable {
		System.out.println("@Around before");
		Object result = jp.proceed();
		System.out.println("@Around after");
		return result;
	}

}
