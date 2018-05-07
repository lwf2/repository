package com.aspire.bpom.extensions;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

public class PropertiesFactoryBean implements ApplicationContextAware, FactoryBean<Resource[]>{
	private static Logger logger = LoggerFactory.getLogger(PropertiesFactoryBean.class);
	
	private ApplicationContext applicationContext;
	
	protected String envPrefix = "%{";

    protected String envSuffix = "}%";
	
	private String[] propertyLocations;
	
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		this.applicationContext = arg0;
	}

	@Override
	public Resource[] getObject() throws Exception {
		Resource[] resArray = null;
        if (propertyLocations != null && propertyLocations.length > 0) {
            for (String location : propertyLocations) {
                String loc = location;
                try {
                    int startIndex = location.indexOf(envPrefix);
                    int endIndex = location.indexOf(envSuffix);
                    if (startIndex > -1 && endIndex > startIndex) {
                        String envName = location.substring(startIndex + envPrefix.length(), endIndex);
                        String envValue = System.getenv(envName.trim());
                        loc = location.replace(envPrefix + envName + envSuffix, envValue);
                        logger.info("convert '{}' to '{}'", location, loc);
                    }
                    resArray = applicationContext.getResources(loc);
                    if (resArray != null && resArray.length != 0) {
                        logger.info("resources found at location {}", location);
                        break;
                    } else {
                        logger.warn("no resources found at location pattern '{}'", location);
                    }
                } catch (Exception e) {
                    logger.warn("no resources found at location pattern '{}'", location);
                }
            }
        }
        if (resArray == null || propertyLocations.length == 0) {
            logger.error("no resources found at loction patterns {}", Arrays.toString(propertyLocations));
        }
        return resArray;
	}

	@Override
	public Class<?> getObjectType() {
		return Resource.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	public String[] getPropertyLocations() {
		return propertyLocations;
	}

	public void setPropertyLocations(String[] propertyLocations) {
		this.propertyLocations = propertyLocations;
	}
}
