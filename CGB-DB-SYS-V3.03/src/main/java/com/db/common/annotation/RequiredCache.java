package com.db.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 自定义注解
 * @author 全日制
 *
 */

@Retention(RetentionPolicy.RUNTIME)//运行时有效
@Target(ElementType.METHOD)//只能修饰方法
public @interface RequiredCache {
	

}
