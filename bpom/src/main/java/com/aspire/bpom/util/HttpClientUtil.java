package com.aspire.bpom.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.slf4j.Logger;

import com.aspire.bpom.extensions.exception.BusinessException;
import com.aspire.bpom.extensions.exception.NetworkException;
import com.aspire.bpom.extensions.log4j.BpomLogger;

public class HttpClientUtil {
	private static final Logger log = BpomLogger.getLogger(HttpClientUtil.class);
	private volatile static MultiThreadedHttpConnectionManager connMgr;

	//连接超时：单位ms
	static private int MAX_CONNECT_TIMEOUT = 30000; 
	
	//读取超时：单位ms
	static private int MAX_SO_TIMEOUT = 30000; 
	
	//最大连接数
	static private int MAX_TOTAL_CONS = 2048; 
	
	//单域名最大连接数
	static private int MAX_TOTAL_CONS_PER_HOST = 128; 
	
	public static String doHttpPost(String postUrl, Map<String, String> head){

		PostMethod post = null;
		String ret = null;
		
		try {
			post = new PostMethod(postUrl);
			post.setRequestHeader("Content-Type", "application/xml; charset=UTF-8");
			if (head != null) {
				Set<String> keys = head.keySet();
				for (Iterator<String> i = keys.iterator(); i.hasNext();) {
					String key = (String) i.next();
					post.addRequestHeader(key, head.get(key));
				}
			}
		
			HttpClient httpclient = new HttpClient();
			HttpClientParams hcp = new HttpClientParams();
//			hcp.setSoTimeout(MAX_SO_TIMEOUT);
//			hcp.setConnectionManagerTimeout(MAX_CONNECT_TIMEOUT);
			httpclient.setParams(hcp);
		
			httpclient.setHttpConnectionManager(getConnMgr());
		
			int result = httpclient.executeMethod(post);
			if (result == HttpStatus.SC_OK) {
				ret = post.getResponseBodyAsString();
			}else{
				log.error("访问 URL连接异常：");
			}
		} 
		catch (Exception e) {
			log.error("访问 URL连接异常：",e);
		} 
		finally {
			if (post != null) {
				post.releaseConnection();				
			}
			
		}

		return ret;

	}
	
	private static String doHttpPost(String postUrl, String postXml)throws NetworkException {

		PostMethod post = null;
		String ret = null;
		
		try {
		
			post = new PostMethod(postUrl);
			post.setRequestEntity(new StringRequestEntity(postXml, "utf-8", "utf-8"));
			post.setRequestHeader("Content-type", "text/xml; charset=utf-8");
			post.addRequestHeader("Connection", "close");
		
			HttpClient httpclient = new HttpClient();
			HttpClientParams hcp = new HttpClientParams();
//			hcp.setSoTimeout(MAX_SO_TIMEOUT);
//			hcp.setConnectionManagerTimeout(MAX_CONNECT_TIMEOUT);
			httpclient.setParams(hcp);
		
			httpclient.setHttpConnectionManager(getConnMgr());
		
			int result = httpclient.executeMethod(post);
			if (result == HttpStatus.SC_OK) {
				String retTmp = post.getResponseBodyAsString();
				
				ret = retTmp;
				
			} 
			else {
				throw new NetworkException("E1113", result, postUrl);
			}
		} 
		catch (Exception e) {
			log.error("访问 URL连接异常：",e);
			throw new NetworkException("访问 URL连接异常：" + postUrl, e);
		} 
		finally {
			if (post != null) {
				post.releaseConnection();				
			}
			
		}

		return ret;

	}

	
	// 处理同步订购关系返回结果
	public static String doHttpResult(String postUrl, String postXml){
		String ret = "";
		try {
			log.info("接口请求URL：" + postUrl);
			log.info("接口请求内容：" + postXml);
			ret = doHttpPost(postUrl, postXml);
		}
		catch (NetworkException e) {
			log.error("Read Exception of URL return :" + postUrl, e);
		} catch (Exception e) {
			log.error("请求其它异常："+e);
		}
		log.info("接口响应内容：" + ret);
		return ret;
	}

	public static void doHttpNoResult(String postUrl, String postXml)
			throws BusinessException {
		doHttpPost(postUrl, postXml);
	}

	/**
	 * 优化获得MultiThreadedHttpConnectionManager
	 * 
	 * @return
	 */
	public static MultiThreadedHttpConnectionManager getConnMgr() {

		if (connMgr == null) {
			synchronized (MultiThreadedHttpConnectionManager.class) {
				if (connMgr == null) {
					connMgr = new MultiThreadedHttpConnectionManager();
					
					// 设置client连接池参数
					HttpConnectionManagerParams params = connMgr.getParams();
			        params.setConnectionTimeout(MAX_CONNECT_TIMEOUT); 
			        params.setSoTimeout(MAX_SO_TIMEOUT); 
			        
			        params.setDefaultMaxConnectionsPerHost(MAX_TOTAL_CONS_PER_HOST);	//very important!! 
			        params.setMaxTotalConnections(MAX_TOTAL_CONS);		//very important!! 

				}
			}
		}
		
		return connMgr;
	}
	
	public static void main(String[] args) {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "clientId=\"300008880753\",uniqueId=\"2Vq4gaTkPxxxxxxxxxxxxxROJq0uKJd8_k=\",accessToken=\"%2fM7yYCnI81xxxxxxxxxxxxxxxxxxxx4C8ULaE%3d\",apptype=\"1\"");
		

		String url = "https://open.mmarket.com/omp/2.0/idmp/getUserInfo";
		String ret = HttpClientUtil.doHttpPost(url,headers);
		System.out.println("ret==="+ret);
//		JSONObject jsonObj = new JSONObject(ret);
//		System.out.println("resultCode==="+jsonObj.getString("resultCode"));
//		if("401001".equals(jsonObj.getString("resultCode"))){
//			System.out.println("msisdn==="+jsonObj.getString("msisdn"));
//		}
	}
}
