package com.db.common.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.db.sys.service.realm.ShiroUserRealm;

@Configuration
public class SpringShiroConfig {
	// 这个bean去整合第三方，把这个方法的返回值的对象交给spring去管理， @Bean注解中的内容为对象的key
	@Bean("securityManager") // 将bean交给spring 去管理
	public SecurityManager newSecurityManager(ShiroUserRealm shUserRealm, CacheManager cacheManager,
			RememberMeManager rememberMeManager) {
		DefaultWebSecurityManager sm = new DefaultWebSecurityManager();
		sm.setRealm(shUserRealm);
		sm.setCacheManager(cacheManager);
		sm.setRememberMeManager(rememberMeManager);
		sm.setSessionManager(newDefaultWebSessionManager());// 没有@Bean直接调方法
		return sm;
	}

	@Bean("shiroFilterFactory")
	public ShiroFilterFactoryBean newShiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean fbean = new ShiroFilterFactoryBean();
		fbean.setSecurityManager(securityManager);
		fbean.setLoginUrl("/doLoginUI.do");
		Map<String, String> fileterMap = new LinkedHashMap<>();
		fileterMap.put("/bower_components/**", "anon");
		fileterMap.put("/build/**", "anon");
		fileterMap.put("/dist/**", "anon");
		fileterMap.put("/plugins/**", "anon");

		fileterMap.put("/user/doLogin.do", "anon");
		fileterMap.put("/doLogOut.do", "logout");
		// 上面的都是允许的，下面都是要验证的
		fileterMap.put("/**", "authc");

		fbean.setFilterChainDefinitionMap(fileterMap);

		return fbean;
	}

	// @Bean注解没有指定名字时，默认Bean的名字为方法名
	@Bean("lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor newLifecycleBeanPostProcessor() {

		return new LifecycleBeanPostProcessor();
	}

	@DependsOn("lifecycleBeanPostProcessor")
	@Bean
	public DefaultAdvisorAutoProxyCreator newDefaultAdvisorAutoProxyCreator() {

		return new DefaultAdvisorAutoProxyCreator();
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor newAuthorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();

		advisor.setSecurityManager(securityManager);
		return advisor;
	}

	// 配置缓存管理器
	@Bean
	public MemoryConstrainedCacheManager newMemoryConstrainedCacheManager() {
		return new MemoryConstrainedCacheManager();
	}

	// 配置记住我
	@Bean
	public CookieRememberMeManager newCookieRememberMeManager() {
		CookieRememberMeManager cookieManager = new CookieRememberMeManager();
		SimpleCookie cookie = new SimpleCookie("remember");
		cookie.setMaxAge(24 * 7 * 60 * 60);
		cookieManager.setCookie(cookie);
		return cookieManager;
	}

	public DefaultWebSessionManager newDefaultWebSessionManager() {
		DefaultWebSessionManager sManager = new DefaultWebSessionManager();
		sManager.setGlobalSessionTimeout(32130000);
		sManager.setDeleteInvalidSessions(true);
		sManager.setSessionValidationSchedulerEnabled(true);
		return sManager;
	}

}
