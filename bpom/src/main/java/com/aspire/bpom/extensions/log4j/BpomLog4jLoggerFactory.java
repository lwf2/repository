package com.aspire.bpom.extensions.log4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.spi.AbstractLoggerAdapter;
import org.apache.logging.log4j.spi.LoggerContext;
import org.apache.logging.log4j.util.ReflectionUtil;
import org.slf4j.Logger;


public class BpomLog4jLoggerFactory extends AbstractLoggerAdapter<Logger>{
	
	 private static final String FQCN = BpomLog4jLoggerFactory.class.getName();
	 private static final String PACKAGE = "org.slf4j";
	    
	 @Override
	 protected Logger newLogger(final String name, final LoggerContext context) {
	      final String key = Logger.ROOT_LOGGER_NAME.equals(name) ? LogManager.ROOT_LOGGER_NAME : name;
	      return new BpomSLF4JLogger(context.getLogger(key), name);
	 }

	 @Override
	 protected LoggerContext getContext() {
	      final Class<?> anchor = ReflectionUtil.getCallerClass(FQCN, PACKAGE);
	      return anchor == null ? LogManager.getContext() : getContext(ReflectionUtil.getCallerClass(anchor));
	 }

}
