package com.aspire.rabbit.mq1;

import org.apache.commons.lang.StringUtils;

public class Test {

	public static void main(String[] args) {
		String message = "nihaoma 你好";
		String[] temp = StringUtils.split(message, " ");
		System.out.println(temp[0] + ", " + temp[1]);
	}
}
