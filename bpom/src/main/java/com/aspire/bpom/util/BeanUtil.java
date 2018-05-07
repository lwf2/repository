package com.aspire.bpom.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import com.aspire.bpom.extensions.log4j.BpomLogger;

public class BeanUtil {

	private static final Logger log = BpomLogger.getLogger(BeanUtil.class);

	/**
	 * 将javabean转换为以属性名为key，属性值为value的Map;
	 * 
	 * @param obj
	 *            javabean
	 * @param includeNull
	 *            是否包含属性值为null的键值对
	 * @return
	 */
	public static ListMap<String, String> transBeanToMap(Object obj, boolean includeNull) {

		if (obj == null) {
			return null;
		}
		ListMap<String, String> map = new ListMap<String, String>();
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(obj.getClass());
		} catch (Exception e) {
			return null;
		}
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor property : propertyDescriptors) {
			String key = property.getName();

			// 过滤class属性
			if (!key.equals("class")) {
				// 得到property对应的getter方法
				Method getter = property.getReadMethod();
				Object value = null;
				try {
					value = getter.invoke(obj);
				} catch (Exception e) {
					// e.printStackTrace();
				}
				if (value == null && !includeNull) {
					continue;
				}
				map.put(key, String.valueOf(value));
			}

		}

		return map;

	}

	public static <T> T transMapToBean(Map<String, ? extends Object> map, Class<T> clazz) {
		T bean;
		try {
			bean = clazz.newInstance();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return null;
		}

		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(clazz);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return null;
		}
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor property : propertyDescriptors) {
			String key = property.getName();
			if (!"class".equals(key)) {
				Object value = map.get(key);

				Method setter = property.getWriteMethod();
				try {
					setter.invoke(bean, value);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}

		return bean;
	}

	public static <T> T paramStringToBean(String params, Class<T> clazz) {
		Map<String, String> paramMap = new HashMap<String, String>();
		String[] paramArray = params.split("&");
		for (String param : paramArray) {
			int index = param.indexOf("=");
			if (index > 0 && index < param.length() - 1) {
				String key = param.substring(0, index);
				String value = URLCoder.decode(param.substring(index + 1));
				paramMap.put(key, value);
			}
		}
		return transMapToBean(paramMap, clazz);
	}
}
