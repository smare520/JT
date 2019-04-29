package com.jt.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 切面类
 *	
 */
//@Component
//@Aspect
public class HelloAop {
	/**
	 *  切入点表达式的写法:
	 * 	1.within("包名.类名")   粗粒度
	 * 	匹配的类中的全部方法都会执行通知方法.
	 * 	
	 *  2.excution("返回值类型 包名.类名.方法名(参数列表)")
	 * 	
	 */
	//1.定义前台通知
	@Before("within(com.jt.controller.HelloController)")
	public void before(JoinPoint jionpoint) {
		System.out.println("前置通知~~~");
		String name = jionpoint.getSignature().getName();
		System.out.println("获取目标方法名称"+name);
		Class<? extends Object> class1 = jionpoint.getTarget().getClass();
		System.out.println("获取目标方法类型"+class1);
	}


	/**
	 * 环绕通知配置如下
	 * 	1.必须写切入点表达式
	 * 	2.必须添加ProceedingJoinPoint,必须位于参数第一位
	 * 	3.必须配置返回值 并且为object类型
	 * @param joinPoint
	 * @return
	 */
	//定义环绕通知  环绕通知必须加object
	//	@Around("within(com.jt.controller.HelloController)")
	@Around("execution(* com.jt.controller.*.add*(..))")
	public Object around(ProceedingJoinPoint pjp){

		Object object=null;
		try {
			System.out.println("环绕通知开始");
			//没有对方法放行
			object = pjp.proceed();
			System.out.println("环绕通知结束");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return object;

	}

	/**
	 * 后台通知 要求controller中的全部方法都执行后置通知
	 * execution(* com.jt.controller.*.add*(..)
	 * 								一级目录
	 */
	@AfterReturning("execution(* com.jt.controller..*.*(..))")
	public void afterReturn(JoinPoint jp) {
		System.out.println("后置通知");
	}

	//AOp最终通知
	@After("execution(* com.jt.controller..*.*(..))")
	public void after() {
		System.out.println("我是最终通知");
	}











}
