package com.aspire.bpom.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.StringUtils;

/**
 * TODO　手机号伪码生成规则
 * @author lwg
 * @version 1.0
 * 2017年8月14日 上午10:49:44
 */
public class HEXUtil {

	public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static String ALGORITHM = "MD5";
	private static String CHARSET = "UTF-8";
	final static String key = "Mv3iT4cUu1nAES3u";
	
	public static String getUserPseudoCode(String mobile){
		String str = "";
		if(StringUtils.isBlank(mobile)){
			return null;
		}
		String lStr = mobile.substring(7);// 后四位
		String fStr = mobile.substring(0, 3);// 前3位
		String cStr = mobile.substring(3, 7);// 中间4位
		int add = 0;
		for (int i = 0; i < lStr.length(); i++) {
			add += Character.getNumericValue(lStr.charAt(i));
		}
		str = key + lStr + fStr + cStr + add;
		return getCode(str);
	}
    
    /**
	 * 对待签字符串进行MD5摘要算法计算，将所得值转换成16进制串（大写）后返回
	 * 
	 * @param preSign
	 * @return
	 */
	public static String getCode(String preSign) {

		if (preSign == null) {
			return null;
		}
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance(ALGORITHM);
			messageDigest.reset();
			messageDigest.update(preSign.getBytes(CHARSET));
		} catch (NoSuchAlgorithmException e) {
			return preSign;
		} catch (UnsupportedEncodingException e) {
			return preSign;
		}

		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString().toUpperCase();
	}
	
	public static void main(String[] args) {
		/*String mobile = "15102704812";
		String lStr = mobile.substring(7);
		System.out.println("后四位"+lStr);
		String fStr = mobile.substring(0, 3);
		String center = mobile.substring(3, 7);
		int add = 0;
		for (int i = 0; i < lStr.length(); i++) {
			add += Character.getNumericValue(lStr.charAt(i));
		}
		System.out.println(generateString(16));*/
		String aaString = HEXUtil.getUserPseudoCode("15102704812");
		System.out.println(aaString);
		/*System.out.println(new HEXUtil().getCode(aaString));
		System.out.println();*/
	}
}
