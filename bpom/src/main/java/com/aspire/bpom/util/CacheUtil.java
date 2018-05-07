package com.aspire.bpom.util;

import java.util.Hashtable;

public class CacheUtil {
	private static Hashtable<String,String> config;
	
	public static Hashtable<String, String> getConfig() {
		return config;
	}
	public static void setConfig(Hashtable<String, String> config) {
		CacheUtil.config = config;
	}	
}
