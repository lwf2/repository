package com.aspire.bpom.util;

import com.aspire.bpom.extensions.log4j.BpomLogger;
import com.ning.http.client.*;
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;
import com.ning.http.client.AsyncHttpClientConfig.Builder;

import org.slf4j.Logger;

import java.util.Map;
import java.util.Map.Entry;

public class HttpRequestUtil {

    private static final Logger log = BpomLogger.getLogger(HttpRequestUtil.class);
    private static final AsyncHttpClient asyncHttpClient;
    static {
        Builder builder = new Builder();
        builder.setConnectionTimeoutInMs(1000 * 10);
        builder.setMaxConnectionLifeTimeInMs(1000 * 30);
        AsyncHttpClientConfig asyncHttpClientConfig = builder.build();
        asyncHttpClient = new AsyncHttpClient(asyncHttpClientConfig);
    }

    public static void close() {
        asyncHttpClient.closeAsynchronously();
    }

    public static String postXML(String strURL, String xml, final String encode) {
        try {
            log.debug("发送post请求到" + strURL);
            log.debug("请求内容：" + xml);
            BoundRequestBuilder post = asyncHttpClient.preparePost(strURL);
            post.setHeader("Content-Type", "application/xml;charset=" + encode);
            post.setBody(xml);
            ListenableFuture<String> execute = post.execute(new AsyncCompletionHandler<String>() {
                @Override
                public String onCompleted(Response response) throws Exception {
                    return response.getResponseBody(encode);
                }
            });
            return execute.get();
        } catch (Exception e) {
            log.error("post con error ", e);
            throw new RuntimeException("post con error:" + e.getMessage());
        }
    }

    public static String postXML(String address, String xml) {
        return postXML(address, xml, "UTF-8");
    }

    public static String postForm(String strURL, Map<String, String> paramMap, final String encode) {
        try {
            log.debug("发送post请求到" + strURL);
            BoundRequestBuilder post = asyncHttpClient.preparePost(strURL);
            post.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + encode);
            FluentStringsMap fluentStringsMap = new FluentStringsMap();
            for (Entry<String, String> entry : paramMap.entrySet()) {
                fluentStringsMap.add(entry.getKey(), entry.getValue());
            }
            post.setParameters(fluentStringsMap);
            ListenableFuture<String> execute = post.execute(new AsyncCompletionHandler<String>() {
                @Override
                public String onCompleted(Response response) throws Exception {
                    return response.getResponseBody(encode);
                }
            });
            return execute.get();
        } catch (Exception e) {
            log.error("post con error ", e);
            throw new RuntimeException("post con error:" + e.getMessage());
        }
    }

    public static String postForm(String strURL, Map<String, String> paramMap) {
        return postForm(strURL, paramMap, "UTF-8");
    }

    public static String get(String strURL) {
        return get(strURL, "UTF-8");
    }

    public static String get(String strURL, final String encode) {
        try {
            log.debug("发送get请求到" + strURL);
            BoundRequestBuilder get = asyncHttpClient.prepareGet(strURL);
            get.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + encode);
            ListenableFuture<String> execute = get.execute(new AsyncCompletionHandler<String>() {
                @Override
                public String onCompleted(Response response) throws Exception {
                    return response.getResponseBody(encode);
                }
            });
            return execute.get();
        } catch (Exception e) {
            log.error("get con error ", e);
            throw new RuntimeException("get con error:" + e.getMessage());
        }
    }
}
