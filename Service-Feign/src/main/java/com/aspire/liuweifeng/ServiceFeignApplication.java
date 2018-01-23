package com.aspire.liuweifeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

//向服务器中心注册
@EnableDiscoveryClient
//开启Feign的功能
@EnableFeignClients
@SpringBootApplication
//开启hystrixDashboard仪表盘，便于监控
@EnableHystrixDashboard
//开启断路器功能
@EnableCircuitBreaker
public class ServiceFeignApplication {

	/**
	 * Feign是一个声明式的伪Http客户端，它使得写Http客户端变得更简单。使用Feign，
	 * 只需要创建一个接口并注解。它具有可插拔的注解特性，可使用Feign 注解和JAX-RS注解。
	 * Feign支持可插拔的编码器和解码器。Feign默认集成了Ribbon，并和Eureka结合，默认实现了负载均衡的效果
	 * @param args
	 * @author liuweifeng
	 */
	public static void main(String[] args) {
		SpringApplication.run(ServiceFeignApplication.class, args);
	}
}
