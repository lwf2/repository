package com.aspire.liuweifeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

//向eureka服务中心注册,和@EnableDiscoveryClient基本一致，前者基本较为单一，在使用eureka作为服务器中心时使用，但是后者在其它服务中心时也可
@EnableEurekaClient
//开启Zuul路由网关
@EnableZuulProxy
@SpringBootApplication
public class ServiceZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceZuulApplication.class, args);
	}
}
