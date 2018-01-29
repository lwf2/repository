package com.aspire.rabbit.mq;

import com.rabbitmq.client.ConnectionFactory;

public class Send {

	private final static String QUEUE_NAME = "hello";
	
	public static void main(String[] args) throws java.io.IOException {
		//创建连接连接到rabbitmq
		ConnectionFactory factory = new ConnectionFactory();
		//设置rabbitMQ所在主机ip或者主机名
	}
}
