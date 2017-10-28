package com.zongcc.controller;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class SpringContextHolder implements ApplicationContextAware {

	private  static ApplicationContext applicationContext;

//	private static Logger logger = LoggerFactory.getLogger(SpringContextHolder.class);


	/**
     * 取得存储在静态变量中的ApplicationContext.
     */
	public static Object getBean(String name) {
		return  applicationContext.getBean(name);
	}

	/**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
	public static  <T> T getBean(Class<T> requiredType) {
		return applicationContext.getBean(requiredType);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
	

}
