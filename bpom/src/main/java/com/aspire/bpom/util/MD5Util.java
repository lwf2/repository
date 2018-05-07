package com.aspire.bpom.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.lang3.StringUtils;


public class MD5Util {
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
	
	/**
	 * 获取实体类的值，并拼装成字符串。
	 * @param obj
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static String getEntityStr(Object obj) throws InstantiationException, IllegalAccessException{
		//获取该实体类的class
		Class clazz = obj.getClass();
		StringBuffer buffer = new StringBuffer();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);//设置这些属性是可以访问的  
			Object attValue = field.get(obj);//获取属性值
			//serialVersionUID属性值不获取，或者属性值为空的不能获取
			if("serialVersionUID".equals(field.getName()) ||
					"hmac".equals(field.getName()) ||
					       attValue == null){
				continue;
			}
			buffer.append(attValue);
		}
		return buffer.toString();
	}
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
//		/*String mobile = "15102704812";
//		String lStr = mobile.substring(7);
//		System.out.println("后四位"+lStr);
//		String fStr = mobile.substring(0, 3);
//		String center = mobile.substring(3, 7);
//		int add = 0;
//		for (int i = 0; i < lStr.length(); i++) {
//			add += Character.getNumericValue(lStr.charAt(i));
//		}
//		System.out.println(generateString(16));*/
//		String aaString = MD5Util.getUserPseudoCode("15102704812");
//		System.out.println(aaString);
//		/*System.out.println(new HEXUtil().getCode(aaString));
//		System.out.println();*/
//		
//		QryContractResp qryContractResp = new QryContractResp();
//		qryContractResp.setMsgType("QryContractResp");
//		qryContractResp.setVersion("1.0.0");
//		qryContractResp.setReturnCode("1");//处理失败
//		qryContractResp.setReturnMsg("失败");
//		qryContractResp.setA(333333);
//		qryContractResp.setB(999999);
//		
//		System.out.println("得到的数据是：" + getCode(getEntityString(qryContractResp)));
	}
}
