package com.aspire.bpom.extensions;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class PropertyConfigurator extends PropertyPlaceholderConfigurer {
	
	private static Map<Object, Object> configMap = new ConcurrentHashMap<Object, Object>();
	
	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) throws BeansException {
		super.processProperties(beanFactory, props);
		for (Map.Entry<Object, Object> entry : props.entrySet()) {
			configMap.put(entry.getKey(), entry.getValue());
		}
	}
	
	public static Object getConfig(Object obj){
		return configMap.get(obj);
	}
	
	public static String getConfig(String key){
		return (String) configMap.get(key);
	}
}
