package com.db.common.web;

import org.apache.log4j.spi.LoggerFactory;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.springframework.beans.factory.config.ListFactoryBean;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.db.common.vo.JsonResult;
//全局异常处理类
@ControllerAdvice
public class GlobalExceptionHandler{
	
//	private Logger log=LoggerFactory.get;//日志接口，
	
	@ExceptionHandler(RuntimeException.class)//修饰注解的方法为异常处理
	@ResponseBody//这个是把返回结果转换为串
	public JsonResult doHandleRuntimeException(RuntimeException e) {
		e.printStackTrace();
		return new JsonResult(e);
	}
	
	@ExceptionHandler(ShiroException.class)//处理异常的类型
	@ResponseBody
	public JsonResult doHandleShiroException(ShiroException e) {
		e.printStackTrace();//先打印  看看啥问题
		JsonResult result = new JsonResult();
		result.setState(0);
		if (e instanceof UnknownAccountException) {
			result.setMessage("账户不存在");
		}else if(e instanceof LockedAccountException) {
			result.setMessage("账户已被禁用");
		}else if(e instanceof IncorrectCredentialsException) {
			result.setMessage("密码不正确");
		}else if(e instanceof AuthorizationException) {
			result.setMessage("没有此操作权限");
		}else {
			result.setMessage("系统维护中");
		}

		
		return result;
	}

}
