package com.aspire.bpom.extensions;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextHandler implements ApplicationContextAware{
	private static ApplicationContext ctx;
	
	@SuppressWarnings("static-access")
	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {	
		this.ctx = ctx;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name){
		return (T) ctx.getBean(name);
	}
}
