package com.jt.util;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CaCheAnnotation {
	int index() default 0;	//默认为第一个参数
	CACHE_TYPE cacheType() default CACHE_TYPE.FIND;

	enum CACHE_TYPE {
		FIND,UPDATE;
	}
}
