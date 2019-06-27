package com.db.common.web;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.db.common.exception.ServiceException;

//基于回调去实现 
/**
 * spring MVC 中的handler拦截器定义 需求：基于此拦截器
 * 
 * @author 全日制
 *
 */
@Component
public class TimeAccessInterceptor extends HandlerInterceptorAdapter {
	/**
	 * 此方法在后端控制器方法执行之前执行
	 * 
	 * 次返回值 决定请求继续 还是到此为止。 
	 * true, 继续往后面走
	 *  false 到此为止。
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {// 在后端控制器执行之前 执行，拦截，posetHandle 是之后执行
		System.out.println("request interceptor");
		// 1.获取一个日历对象（通过此对象设置时间）
		Calendar calendar = Calendar.getInstance();
		// 2.设置开始允许访问时间
		calendar.set(Calendar.HOUR_OF_DAY, 6);// 小时每天九点之前可以访问
		calendar.set(Calendar.MINUTE, 0);// 分钟
		calendar.set(Calendar.SECOND, 0);// 秒
		long start = calendar.getTimeInMillis();
		// 3.设置结束允许访问时间
		calendar.set(Calendar.HOUR_OF_DAY, 2);
		long end = calendar.getTimeInMillis();
			//4.
		long time = System.currentTimeMillis();
//		if (time < start || time  > end) {
//			throw new ServiceException("此时间不允许访问");
//		}
		return true;
	}
}
