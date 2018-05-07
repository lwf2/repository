package com.aspire.bpom.extensions.log4j;

import java.util.Date;

import com.aspire.bpom.util.DateTimeUtil;
import com.aspire.bpom.util.RandomUtil;

public class ThreadLocalTransactionID {
	private static ThreadLocal<String> localTransactionID = new ThreadLocal<String>();

	public static void setTransactionID(String transactionID) {
		localTransactionID.set(transactionID);
	}
	
	public static String getTransactionID() {
		String transactionID = localTransactionID.get();
		
		if (transactionID == null) {
			return "NotSetTransactionID";
		} else {
			return transactionID;
		}
	}
	
	/**
	 * TransactionID = 当前时间 + 5位随机数
	 * 
	 * @return
	 */
	public static String generateTransactionID() {
		return DateTimeUtil.format(new Date(),false) + RandomUtil.generateNumberString(5);
	}
	
	/**
	 * TransactionID = 手机号 + 5位随机数
	 * 如果手机号没有的话，则用当前时间 + 5位随机数
	 * 
	 * @param destmsisdn
	 * @return
	 */
	public static String generateTransactionID(String order) {
		if (order == null || order.equals("")) {
			return DateTimeUtil.format(new Date(),false) + RandomUtil.generateNumberString(5);
		} else {
			return order + RandomUtil.generateNumberString(5);
		}
	}
	
	/**
	 * TransactionID = "M" + 手机号 + MMddHHmm + 3位随机数
	 * 如果手机号没有的话，则用当前时间 + 5位随机数
	 * 
	 * @param destmsisdn
	 * @return
	 */
	public static String generateMdoTransactionID(String order) {
		String date = DateTimeUtil.format(new Date(),"MMddHHmm");
		return "M" + order + date + RandomUtil.generateNumberString(3);
	}
	
}
