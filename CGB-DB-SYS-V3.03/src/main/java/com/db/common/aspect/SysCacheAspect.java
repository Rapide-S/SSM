package com.db.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;


/**
 * 对查询的方法做缓存处理
 *
 */
@Aspect  //他是一个切面
@Service		//交给spring去管理，这样是一个业务  所以用@Service
public class SysCacheAspect {
	/**
	 * 定义切入点，其中@annotation(...)为注解方式切入点表达式定义
	 */
	//为有这个注解修饰的方法加扩展功能，只有加了这个注解的方法才会具备扩展功能
//	@Pointcut("@annotation(com.db.common.annotation.RequiredCache)")
	@Pointcut("execution(* com.db.sys.service..*.find*(..))")//这个脸方法上的注解都不用加。
	public void doCache() {}
	
	
	 @Around("doCache()")//扩展功能
	 public Object around(ProceedingJoinPoint jp) throws Throwable{
		 //1.先从缓存取数据
		 System.out.println("query data from cache");
		 //2.缓存没有则执行业务
		 System.out.println("execute target method");
		 Object result=jp.proceed();
		 //3.将查询结果存储到缓存对象
		 System.out.println("put data to cache");
		 return result;
	 }
	
}
