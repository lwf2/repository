package com.emily.demo001;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
/**
 * 
 * <p>Title:DemoApplication </p>
 * <p>Description: </p>
 * <p>Project:springboot  的spring batch批处理 </p> 
 *
 * @author admin（2017年7月14日 上午10:13:08）
 *
 * @version:1.0.0 copyright © 2017-2018
 */
@ComponentScan("com.emily")
@SpringBootApplication
public class DemoApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
