package com.aspire.bpom.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class EncodeUtil {
	private static BASE64Encoder enc = new BASE64Encoder();
	private static BASE64Decoder dec = new BASE64Decoder();
	
	public static String BASE64Encode(byte[] en){
		return enc.encode(en);
	}
	
	public static byte[] BASE64Decode(String de) throws Exception{
		return dec.decodeBuffer(de);
	}
}
