package com.db.common.aspect;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.db.common.annotation.RequiredLog;
import com.db.common.util.IPUtils;
import com.db.common.util.ShiroUtil;
import com.db.sys.dao.SysLogDao;
import com.db.sys.entity.SysLog;


/**
 * 通过此类为系统中的某些业务操作添加扩展功能
 * 日志扩展功能
 * @author 全日制
 *
 *@Aspect  描述的类为一个切面对象，这样的类中有两大部分构成：
 *（1）Pointcut:切入点（织入扩展功能的点）
 *（2）Advice:通知（扩展功能）
 *
 */
@Order(1)
@Aspect
@Service //交给spring去管理
public class SysLogAspect {

	@Autowired
	private SysLogDao sysLogDao;
	/**
	 * 定义切入点，其定义要借助@Pointcut
	 * 其中bean(bean的名字或表达式)为切入点表达式
	 * 语法结构：
	 * （1）bean(bean的名字)   例如bean(sysUserServiceImpl)
	 * （2）bean(bean表达式)   例如bean(*ServiceImpl)
	 */
	@Pointcut("bean(sysUserServiceImpl)")//拦截了这个类的所有的方法
	public void doLog() {}
	
	
	/**
	 * @Around此注解描述的方法为一个通知，这个通知可以在目标方法执行前和后做一些事情
	 * @param jp  代表一个连接点对象（封装可目标方法信息）
	 * @return   目标方法的执行结果
	 * @throws Throwable
	 */
	@Around("doLog()")//引用doLog 就相当于拿到了上面那个注解  也可以直接在括号里写bean(sysUserServiceImpl)
	public Object around(ProceedingJoinPoint jp) throws Throwable{
		//参数是连接点，一般指被拦截到的的方法
		long t1=System.currentTimeMillis();
		Object result = jp.proceed();//执行下一个切面方法，没有下一个切面，会执行目标方法
		Method method = getTargetMethod(jp);
		String name = getTargetMethodName(method);
		System.out.println("String name = getTargetMethodName(method);");
		System.out.println("目标方法名："+name);
		//测试输入目标类全名和方法名  ，知道那个类执行了那个方法
		long t2=System.currentTimeMillis();
		
		saveObject(jp,(t2-t1));//调用添加日志
		System.out.println("");
		return result;
		}
	
	
	  /**拼接  获取目标方法的名称:类全名+方法名       --获取方法名，谁执行了什么操作，将来就可以输出日志了*/
	   private String getTargetMethodName(Method method) {
		   return new StringBuilder(method.getDeclaringClass().getName())
		   .append(".").append(method.getName()).toString();
	   }
	/**
	 * 基于连接点信息获取目标方法对象
	 * @param jp
	 * @return
	 * @throws Exception
	 */
	private Method getTargetMethod(ProceedingJoinPoint jp) throws Exception {
		//1.获取目标类对象（字节码对象）
		Class<?> targetCls = jp.getTarget().getClass();
		System.out.println("Class<?> targetCls = jp.getTarget().getClass();");
		System.out.println("目标类全名："+targetCls.getName());
		//2.获取方法签名信息（包含方法名，参数列名）
		MethodSignature ms = (MethodSignature)jp.getSignature();
		//3.获取目标方法对象
		Method method = targetCls.getDeclaredMethod(ms.getName(),ms.getParameterTypes());
		return method;
	}
	 /**将用户行为日志信息写入到数据库*/
	private void saveObject(ProceedingJoinPoint jp,long time) throws Exception {
		//1.获取日志信息,还要获取参数
		String username = ShiroUtil.getUser().getUsername();
		Method method = getTargetMethod(jp);
		String methodname = getTargetMethodName(method);
		String params = Arrays.toString(jp.getArgs());
		
		//这里获取拦截方法上@RequiredLog注解里的参数值，
		RequiredLog rlog = method.getDeclaredAnnotation(RequiredLog.class);
		//没有的话，就添加默认
		String operation = "operation";
		if (rlog!=null&&!StringUtils.isEmpty(rlog.value())) {
			operation=rlog.value();
		}
		String ipAddr = IPUtils.getIpAddr();
		
		SysLog log = new SysLog();
		log.setMethod(methodname);
		log.setParams(params);
		log.setOperation(operation);
		log.setIp(ipAddr);
		log.setUsername(username);
		log.setTime(time);
		log.setCreatedTime(new Date());
		sysLogDao.insertObject(log);
		System.out.println("保存了一次日志");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
