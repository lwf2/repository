package com.aspire.lwf.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LWFController {

	@Value("${document}")
	String liuweifeng;
	
	@RequestMapping(value = "/hi")
	public String hi() {
		System.out.println("=======================================" + liuweifeng);
		return liuweifeng;
	}
}
