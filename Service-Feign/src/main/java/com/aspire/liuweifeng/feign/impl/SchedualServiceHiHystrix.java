package com.aspire.liuweifeng.feign.impl;

import org.springframework.stereotype.Component;

import com.aspire.liuweifeng.feign.SchedualServiceHi;

/**
 * 熔断机制，在调用服务失败的情况下，就是调用这个类
 * @author liuweifeng
 *
 */
@Component
public class SchedualServiceHiHystrix implements SchedualServiceHi {

	@Override
	public String sayHiFromClientOne(String name) {
		// TODO Auto-generated method stub
		return "sorry " + name;
	}

}
