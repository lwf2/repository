package com.aspire.liuweifeng.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.aspire.liuweifeng.feign.impl.SchedualServiceHiHystrix;

/**
 * 定义一个feign接口，通过@FeignClient（“服务名”），来指定调用哪个服务。
 * 比如在代码中调用了service-hi服务的“/hi”接口，代码如下：
 * fallback这个是feign自带的hystrix熔断器
 * @author liuweifeng
 *
 */
@FeignClient(value = "service-hi", fallback = SchedualServiceHiHystrix.class)
public interface SchedualServiceHi {

	@RequestMapping(value = "/hi", method = RequestMethod.GET)
	String sayHiFromClientOne(@RequestParam(value = "name") String name);
}
