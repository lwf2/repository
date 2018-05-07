package com.aspire.bpom.util;

public class Sequence {
	private static int id = 0;
	private static String lastTimestamp = "";
	public static String getSequence(){
		if(!DateUtil.getCurrentTimeStamp().equals(lastTimestamp)){
			lastTimestamp = DateUtil.getCurrentTimeStamp();
			id = 0;
		}else{
			id++;
		}
		return DateUtil.getCurrentTimeStamp() + String.format("%03d", id);
	}
}
