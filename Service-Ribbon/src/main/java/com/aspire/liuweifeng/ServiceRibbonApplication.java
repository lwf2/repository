package com.aspire.liuweifeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

//向服务中心注册
@EnableDiscoveryClient
@SpringBootApplication
//开启Hystrix,Netflix开源了Hystrix组件，实现了断路器模式
@EnableHystrix
//开启hystrixdashboard,仪表盘，便于监控
@EnableHystrixDashboard
public class ServiceRibbonApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceRibbonApplication.class, args);
	}
	
	//创建一个bean，交给spring容器管理
	@Bean
	//表明这个restTemplate开启了负载均衡功能
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
